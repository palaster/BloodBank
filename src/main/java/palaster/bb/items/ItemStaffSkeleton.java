package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IVampiric;
import palaster.bb.entities.EntitySkeletonMinion;

public class ItemStaffSkeleton extends ItemModStaff implements IVampiric {

	public ItemStaffSkeleton(ResourceLocation rl) {
		super(rl);
		powers = new String[]{"bb.staff.skeleton.0", "bb.staff.skeleton.1"};
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, ItemModStaff.getActivePower(stack));
			sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
			sm.setTamed(true);
			sm.setOwnerId(playerIn.getUniqueID());
			sm.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(sm)), null, ItemModStaff.getActivePower(stack));
			worldIn.spawnEntityInWorld(sm);
			stack.damageItem(ItemModStaff.getActivePower(stack) == 1 ? 16 : 8, playerIn);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
