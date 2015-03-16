package palaster97.ss.blocks.tile;

import net.minecraft.item.ItemStack;
import palaster97.ss.items.ItemWorldSoulBinder;

public class TileEntityWorldSoulManipulator extends TileEntityModInventory {

	public TileEntityWorldSoulManipulator() { super(1); }
	
	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public String getName() { return "container.soulSponge"; }
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) { return stack.getItem() instanceof ItemWorldSoulBinder; }
}
