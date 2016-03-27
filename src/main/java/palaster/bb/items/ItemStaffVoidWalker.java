package palaster.bb.items;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityVoid;
import palaster.bb.entities.EntityYinYang;

public class ItemStaffVoidWalker extends ItemModStaff {

	public ItemStaffVoidWalker() {
		super();
		powers = new String[]{"bb.staff.voidWalker.0"};
		setUnlocalizedName("staffVoidWalker");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!playerIn.isSneaking()) {
			worldIn.spawnEntityInWorld(new EntityYinYang(worldIn, playerIn, 0));
			itemStackIn.damageItem(1, playerIn);
		} else {
			worldIn.spawnEntityInWorld(new EntityYinYang(worldIn, playerIn, 1));
			itemStackIn.damageItem(1, playerIn);
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			for(int i = -1; i < 1 + 1; i++)
				for(int k = -1; k < 1 + 1; k++) {
					BlockPos newPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + k);
					if(worldIn.getBlockState(newPos) != null && !(worldIn.getBlockState(newPos).getBlock() instanceof BlockContainer)) {
						if(worldIn.getTileEntity(newPos) == null) {
							IBlockState ogBlockState = worldIn.getBlockState(newPos);
							worldIn.setBlockState(newPos, BBBlocks.touchVoid.getDefaultState());
							if(worldIn.getTileEntity(newPos) != null && worldIn.getTileEntity(newPos) instanceof TileEntityVoid)
								((TileEntityVoid) worldIn.getTileEntity(newPos)).setOriginalBlock(ogBlockState.getBlock());
							stack.damageItem(1, playerIn);
						}
					}
				}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
