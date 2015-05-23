package palaster97.ss.items;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.entities.EntitySkeletonMinion;

public class ItemStaffSkeleton extends ItemModNBTAwakened implements IDuctTappable {

	public ItemStaffSkeleton() {
		super();
		setMaxDamage(1024);
		setUnlocalizedName("staffSkeleton");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(stack.hasTagCompound())	
				if(stack.getTagCompound().getBoolean("IsAwakened")) {
					worldIn.updateEntity(playerIn);
					EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 1);
					sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
					sm.setTamed(true);
					sm.setOwnerId(playerIn.getUniqueID().toString());
					sm.skeletonSpawning(worldIn.getDifficultyForLocation(new BlockPos(sm)), (IEntityLivingData)null, 1);
					worldIn.spawnEntityInWorld(sm);
					stack.damageItem(1, playerIn);
					return true;
				} else {
					worldIn.updateEntity(playerIn);
					EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 0);
					sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
					sm.setTamed(true);
					sm.setOwnerId(playerIn.getUniqueID().toString());
					sm.skeletonSpawning(worldIn.getDifficultyForLocation(new BlockPos(sm)), (IEntityLivingData)null, 0);
					worldIn.spawnEntityInWorld(sm);
					stack.damageItem(1, playerIn);
					return true;
				}
		}
		return false;
	}
}
