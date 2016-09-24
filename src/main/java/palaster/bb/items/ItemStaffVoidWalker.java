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
import palaster.bb.api.capabilities.items.IVampiric;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityVoidTrap;
import palaster.bb.entities.EntityYinYang;

public class ItemStaffVoidWalker extends ItemModStaff implements IVampiric {

	public ItemStaffVoidWalker(String unlocalizedName) {
		super(unlocalizedName);
		powers = new String[]{"bb.staff.voidWalker.0"};
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			if(!playerIn.isSneaking()) {
				EntityYinYang yinYang = new EntityYinYang(worldIn, playerIn, 0);
				yinYang.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
				worldIn.spawnEntityInWorld(yinYang);
				itemStackIn.damageItem(1, playerIn);
				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
			} else {
				EntityYinYang yinYang = new EntityYinYang(worldIn, playerIn, 1);
				yinYang.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
				worldIn.spawnEntityInWorld(yinYang);
				itemStackIn.damageItem(1, playerIn);
				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
			}
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
							worldIn.setBlockState(newPos, BBBlocks.voidTrap.getDefaultState());
							if(worldIn.getTileEntity(newPos) != null && worldIn.getTileEntity(newPos) instanceof TileEntityVoidTrap)
								((TileEntityVoidTrap) worldIn.getTileEntity(newPos)).setOriginalBlock(ogBlockState.getBlock());
							stack.damageItem(1, playerIn);
						}
					}
				}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
