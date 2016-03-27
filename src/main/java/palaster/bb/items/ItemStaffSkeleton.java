package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.entities.EntitySkeletonMinion;

public class ItemStaffSkeleton extends ItemModStaff {

	public ItemStaffSkeleton() {
		super();
		powers = new String[]{"bb.staff.skeleton.0", "bb.staff.skeleton.1"};
		setUnlocalizedName("staffSkeleton");
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			if(ItemModStaff.getActivePower(stack) == 1) {
				EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 1);
				sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
				sm.setTamed(true);
				sm.setOwnerId(playerIn.getUniqueID());
				sm.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(sm)), null);
				worldIn.spawnEntityInWorld(sm);
				stack.damageItem(16, playerIn);
				return EnumActionResult.SUCCESS;
			} else if(ItemModStaff.getActivePower(stack) == 0) {
				EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 0);
				sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
				sm.setTamed(true);
				sm.setOwnerId(playerIn.getUniqueID());
				sm.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(sm)), null);
				worldIn.spawnEntityInWorld(sm);
				stack.damageItem(8, playerIn);
				return EnumActionResult.SUCCESS;
			}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
