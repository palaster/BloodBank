package palaster97.ss.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.core.helpers.SSItemStackHelper;
import palaster97.ss.entities.EntitySkeletonMinion;

public class ItemStaffSkeleton extends ItemModSpecial {

	public ItemStaffSkeleton() {
		super();
		setUnlocalizedName("staffSkeleton");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			int temp = SSItemStackHelper.getItemStackSlotFromPlayer(playerIn, new ItemStack(SSItems.journal));
			if(temp >= 0 && playerIn.inventory.getStackInSlot(temp) != null && playerIn.inventory.getStackInSlot(temp).hasTagCompound())
				if(playerIn.inventory.getStackInSlot(temp).getTagCompound().getInteger("Level") >= 20) {
					playerIn.inventory.getStackInSlot(temp).getTagCompound().setInteger("Level", (playerIn.inventory.getStackInSlot(temp).getTagCompound().getInteger("Level") - 20));
					worldIn.updateEntity(playerIn);
					EntitySkeletonMinion sm = new EntitySkeletonMinion(worldIn);
					sm.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
					sm.setTamed(true);
					sm.setOwnerId(playerIn.getUniqueID().toString());
					worldIn.spawnEntityInWorld(sm);
					return true;
				}
		}
		return false;
	}
}
