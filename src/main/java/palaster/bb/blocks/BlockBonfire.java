package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.entities.careers.CareerUnkindled;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemEstusFlask;
import palaster.bb.world.BBWorldSaveData;

public class BlockBonfire extends BlockMod {

    public BlockBonfire(String unlocalizedName, Material material) {
        super(unlocalizedName, material);
        setHarvestLevel("axe", 0);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
        	IRPG rpg = RPGCapabilityProvider.get(playerIn);
        	if(rpg != null && rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
	            if(heldItem != null) {
	                if(heldItem.getItem() instanceof ItemFireball) {
	                	if(heldItem.stackSize > 1) {
	                		heldItem.stackSize--;
	                		ItemStack flames = new ItemStack(BBItems.flames);
	                		if(!playerIn.inventory.addItemStackToInventory(flames))
	                			worldIn.spawnEntityInWorld(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, flames));
	                	} else
	                		playerIn.setHeldItem(hand, new ItemStack(BBItems.flames));
	                } else if(heldItem.getItem() instanceof ItemGlassBottle) {
	                	if(heldItem.stackSize > 1) {
	                		heldItem.stackSize--;
	                		ItemStack estusFlask = new ItemStack(BBItems.estusFlask);
	                    	if(!estusFlask.hasTagCompound())
	                    		estusFlask.setTagCompound(new NBTTagCompound());
	                    	estusFlask.getTagCompound().setInteger(ItemEstusFlask.TAG_INT_USES, 6);
	                    	if(!playerIn.inventory.addItemStackToInventory(estusFlask))
	                			worldIn.spawnEntityInWorld(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, estusFlask));
	                	} else {
	                		ItemStack estusFlask = new ItemStack(BBItems.estusFlask);
	                    	if(!estusFlask.hasTagCompound())
	                    		estusFlask.setTagCompound(new NBTTagCompound());
	                    	estusFlask.getTagCompound().setInteger(ItemEstusFlask.TAG_INT_USES, 6);
	                    	playerIn.setHeldItem(hand, estusFlask);
	                	}
	                } else if(heldItem.getItem() instanceof ItemEstusFlask) {
	                	if(!heldItem.hasTagCompound())
	                		heldItem.setTagCompound(new NBTTagCompound());
	                	heldItem.getTagCompound().setInteger(ItemEstusFlask.TAG_INT_USES, 6);
	                	playerIn.setHeldItem(hand, heldItem);
	                } else if(heldItem.getItem() == Items.IRON_INGOT) {
	                	if(((CareerUnkindled) rpg.getCareer()).getFocus() >= 100) {
		                	if(heldItem.stackSize > 1) {
		                		heldItem.stackSize--;
		                		ItemStack soulCoin = new ItemStack(BBItems.soulCoin);
		                		if(!playerIn.inventory.addItemStackToInventory(soulCoin))
		                			worldIn.spawnEntityInWorld(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, soulCoin));
		                		((CareerUnkindled) rpg.getCareer()).setFocus(((CareerUnkindled) rpg.getCareer()).getFocus() - 100);
		                	} else {
		                		playerIn.setHeldItem(hand, new ItemStack(BBItems.soulCoin));
		                		((CareerUnkindled) rpg.getCareer()).setFocus(((CareerUnkindled) rpg.getCareer()).getFocus() - 100);
		                	}
	                	}
	                }
	                return true;
	            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if(!worldIn.isRemote)
            if(placer instanceof EntityPlayer) {
            	final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) placer);
            	if(rpg != null)
            		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
            			if(worldIn.provider.getDimension() == 0)
                			return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
            }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if(!worldIn.isRemote)
            if(placer instanceof EntityPlayer) {
            	final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) placer);
            	if(rpg != null)
            		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
            			if(BBWorldSaveData.get(worldIn) != null)
            				BBWorldSaveData.get(worldIn).addBonfire(pos);
            }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(!worldIn.isRemote) {
            BBWorldSaveData bbWorldSaveData = BBWorldSaveData.get(worldIn);
            if(bbWorldSaveData != null)
                bbWorldSaveData.removeBonfire(pos);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
