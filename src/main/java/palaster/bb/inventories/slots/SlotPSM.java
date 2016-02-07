package palaster.bb.inventories.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import palaster.bb.items.ItemPlayerBinder;

public class SlotPSM extends Slot {

	public SlotPSM(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemPlayerBinder;
	}
}
