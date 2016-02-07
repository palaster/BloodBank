package palaster.bb.blocks.tile;

import net.minecraft.item.ItemStack;
import palaster.bb.items.ItemWorldBinder;

public class TileEntityWorldManipulator extends TileEntityModInventory {

	public TileEntityWorldManipulator() { super(1); }
	
	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public String getName() { return "container.worldManipulator"; }
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) { return stack.getItem() instanceof ItemWorldBinder; }
}
