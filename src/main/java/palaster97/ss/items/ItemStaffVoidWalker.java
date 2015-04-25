package palaster97.ss.items;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.blocks.SSBlocks;
import palaster97.ss.blocks.tile.TileEntityVoid;

public class ItemStaffVoidWalker extends ItemModSpecial {

	public ItemStaffVoidWalker() {
		super();
		setUnlocalizedName("staffVoidWalker");
	}
	
	// When pt. at a player will send out a void blast that either dmgs them or teleports them to player
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {}
		return itemStackIn;
	}
	
	// Turns a 3x3, or when augment a 5x5 square into a void trap
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			for(int i = -1; i < 1 + 1; i++)
				for(int k = -1; k < 1 + 1; k++) {
					BlockPos newPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + k);
					if(worldIn.getBlockState(newPos) != null && !(worldIn.getBlockState(newPos).getBlock() instanceof BlockContainer)) {
						if(worldIn.getTileEntity(newPos) == null) {
							IBlockState ogBlockState = worldIn.getBlockState(newPos);
							worldIn.setBlockState(newPos, SSBlocks.touchVoid.getDefaultState());
							if(worldIn.getTileEntity(newPos) != null && worldIn.getTileEntity(newPos) instanceof TileEntityVoid)
								((TileEntityVoid) worldIn.getTileEntity(newPos)).setOriginalBlock(ogBlockState.getBlock());
						}
					}
				}
			return true;
		}
		return false;
	}
}
