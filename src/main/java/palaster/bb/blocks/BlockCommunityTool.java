package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.blocks.tile.TileEntityCommunityTool;

public class BlockCommunityTool extends BlockModContainer {

	public BlockCommunityTool(String unlocalizedName, Material material) {
		super(unlocalizedName, material);
		setBlockUnbreakable();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onItemToss(ItemTossEvent e) {
		if(!e.getPlayer().worldObj.isRemote)
			if(e.getEntityItem().getEntityItem().hasTagCompound() && e.getEntityItem().getEntityItem().getTagCompound().getBoolean(TileEntityCommunityTool.TAG_BOOLEAN)) {
				for(TileEntity te : e.getPlayer().worldObj.loadedTileEntityList) {
					TileEntityCommunityTool ct = (TileEntityCommunityTool) te;
					if(ct != null)
						if(ct.removeUUIDItemStack(e.getPlayer().getUniqueID(), e.getEntityItem().getEntityItem()))
							break;
				}
				e.setCanceled(true);
			}
	}
    
    @SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null && e.getItemStack().hasTagCompound())
			if(e.getItemStack().getTagCompound().getBoolean(TileEntityCommunityTool.TAG_BOOLEAN))
				e.getToolTip().add(I18n.format("bb.misc.countDown"));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,ItemStack stack) {
		if(!worldIn.isRemote)
			if(placer instanceof EntityPlayer) {
				TileEntityCommunityTool ct = (TileEntityCommunityTool) worldIn.getTileEntity(pos);
				if(ct != null)
					ct.setOwner(placer.getUniqueID());
			}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(hand == EnumHand.MAIN_HAND) {
				TileEntityCommunityTool ct = (TileEntityCommunityTool) worldIn.getTileEntity(pos);
				if(ct != null)
					if(ct.getOwner().equals(playerIn.getUniqueID())) {
						if(!playerIn.isSneaking()) {
							if(heldItem != null) {
								if(ct.getItemHandler().getStackInSlot(0) == null) {
									ct.getItemHandler().setStackInSlot(0, playerIn.getHeldItemMainhand());
									playerIn.setHeldItem(EnumHand.MAIN_HAND, null);
								}
							} else {
								if(ct.getItemHandler().getStackInSlot(0) != null) {
									ct.removeAllItemStack();
									playerIn.setHeldItem(EnumHand.MAIN_HAND, ct.getItemHandler().getStackInSlot(0));
									ct.getItemHandler().setStackInSlot(0, null);
								}
							}
						} else if(heldItem == null)
							worldIn.destroyBlock(pos, true);
					} else
						if(heldItem == null)
							if(ct.getItemHandler().getStackInSlot(0) != null)
								if(ct.canAddUUIDItemStack(playerIn.getUniqueID()))
									playerIn.setHeldItem(EnumHand.MAIN_HAND, ct.addUUIDItemStack(playerIn.getUniqueID()));
			}
		}
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) { return new TileEntityCommunityTool(); }
}
