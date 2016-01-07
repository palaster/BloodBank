package palaster97.ss.blocks.tile;

import net.minecraft.item.ItemStack;
import palaster97.ss.items.ItemWorldBinder;

public class TileEntityWorldManipulator extends TileEntityModInventory {

	public TileEntityWorldManipulator() { super(1); }
	
	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public String getName() { return "container.worldManipulator"; }
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) { return stack.getItem() instanceof ItemWorldBinder; }
}
