package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpace extends Container {
	
	public ContainerSpace(InventoryPlayer invPlayer, InventorySpace invSoul) {
		int temp = 0;
		for(int y = 0; y < 6; y++)
			for(int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invSoul, temp, 8 + 18 * x, 18 + y * 18));
				temp++;
			}
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 198));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 140 + y * 18));
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) { return true; }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(p_82846_2_);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (p_82846_2_ < 54) {
                if(!mergeItemStack(itemstack1, 54, inventorySlots.size(), true))
                    return null;
            } else if(!mergeItemStack(itemstack1, 0, 54, false))
                return null;
            if(itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else
                slot.onSlotChanged();
        }
        return itemstack;
	}
}
