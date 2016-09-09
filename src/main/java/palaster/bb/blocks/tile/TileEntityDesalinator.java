package palaster.bb.blocks.tile;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import palaster.bb.items.BBItems;

public class TileEntityDesalinator extends TileEntityModInventory {

	@Override
	public void update() {
		if(!worldObj.isRemote)
			if(getItemHandler().getStackInSlot(0) != null && getItemHandler().getStackInSlot(0).getItem() == Items.WATER_BUCKET) {
				int temp = worldObj.rand.nextInt(4);
				if(temp == 0) {
					if(getItemHandler().getStackInSlot(2) != null && getItemHandler().getStackInSlot(2).stackSize < 64) {
						int temp2 = getItemHandler().getStackInSlot(2).stackSize + 1;
						getItemHandler().setStackInSlot(2, new ItemStack(BBItems.salt, temp2));
					} else
						getItemHandler().setStackInSlot(2, new ItemStack(BBItems.salt, 1));
				}
				if(getItemHandler().getStackInSlot(1) != null && getItemHandler().getStackInSlot(1).stackSize < 64) {
					int temp2 = getItemHandler().getStackInSlot(1).stackSize + 1;
					getItemHandler().setStackInSlot(1, new ItemStack(Items.BUCKET, temp2));
				} else
					getItemHandler().setStackInSlot(1, new ItemStack(Items.BUCKET, 1));
				getItemHandler().setStackInSlot(0, null);
			}
	}

	@Override
	public int getSizeInventory() { return 3; }
}
