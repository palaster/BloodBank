package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster.bb.entities.EntitySkeletonMinion;

public class ItemStaffSkeleton extends ItemModStaff {

	public ItemStaffSkeleton() {
		super();
		powers = new String[]{"bb.staff.skeleton.0", "bb.staff.skeleton.1"};
		setUnlocalizedName("staffSkeleton");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			if(ItemModStaff.getActivePower(stack) == 1) {
				EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 1);
				sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
				sm.setTamed(true);
				sm.setOwnerId(playerIn.getUniqueID().toString());
				sm.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(sm)), null, 1);
				worldIn.spawnEntityInWorld(sm);
				stack.damageItem(16, playerIn);
				return true;
			} else if(ItemModStaff.getActivePower(stack) == 0) {
				EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 0);
				sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
				sm.setTamed(true);
				sm.setOwnerId(playerIn.getUniqueID().toString());
				sm.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(sm)), null, 0);
				worldIn.spawnEntityInWorld(sm);
				stack.damageItem(8, playerIn);
				return true;
			}
		return false;
	}
}