package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemToken;

public class BlockSlotMachine extends BlockMod {

	public BlockSlotMachine(Material material) {
		super(material);
		setUnlocalizedName("slotMachine");
	}
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(heldItem != null) {
            	if(heldItem.getItem() == Items.GOLD_INGOT) {
            		ItemStack token = new ItemStack(BBItems.token);
            		if(!token.hasTagCompound())
                		token.setTagCompound(new NBTTagCompound());
                	token.getTagCompound().setInteger(ItemToken.TAG_INT_TOKEN, -1);
                	if(heldItem.stackSize > 1) {
                		heldItem.stackSize--;
                    	if(!playerIn.inventory.addItemStackToInventory(token))
                			worldIn.spawnEntityInWorld(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, token));
                	} else
                    	playerIn.setHeldItem(hand, token);
                }
                return true;
            }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}
