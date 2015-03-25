package palaster97.ss.items;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.EntitySkeletonMinion;

public class ItemStaffSkeleton extends ItemModNBTAwakened {

	public ItemStaffSkeleton() {
		super();
		setUnlocalizedName("staffSkeleton");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(stack.hasTagCompound())
				if(playerIn.inventory.hasItem(SSItems.journal)) {
					if(stack.getTagCompound().getBoolean("IsAwakened")) {
						if(SSPlayerHelper.getJournalAmount(playerIn) >= 40)
							if(SSPlayerHelper.reduceJournalAmount(playerIn, 40)) {
								worldIn.updateEntity(playerIn);
								EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 1);
								sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
								sm.setTamed(true);
								sm.setOwnerId(playerIn.getUniqueID().toString());
								sm.skeletonSpawning(worldIn.getDifficultyForLocation(new BlockPos(sm)), (IEntityLivingData)null, 1);
								worldIn.spawnEntityInWorld(sm);
								return true;
							}
					} else {
						if(SSPlayerHelper.getJournalAmount(playerIn) >= 20)
							if(SSPlayerHelper.reduceJournalAmount(playerIn, 20)) {
								worldIn.updateEntity(playerIn);
								EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn, 0);
								sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
								sm.setTamed(true);
								sm.setOwnerId(playerIn.getUniqueID().toString());
								sm.skeletonSpawning(worldIn.getDifficultyForLocation(new BlockPos(sm)), (IEntityLivingData)null, 0);
								worldIn.spawnEntityInWorld(sm);
								return true;
							}
					}
				}
		}
		return false;
	}
}
