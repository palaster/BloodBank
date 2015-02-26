package palaster97.ss.inventories.slots;

import palaster97.ss.items.ItemSoulBinder;
import palaster97.ss.items.SSItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class SlotPSM extends Slot {

	public SlotPSM(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack != null)
			if(stack.getItem() instanceof ItemPotion || stack.getItem() instanceof ItemSoulBinder || stack.getItem().equals(new ItemStack(SSItems.mobSouls, 1, 1).getItem()))
				return true;
		return false;
	}
}
