package palaster.bb.inventories;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import palaster.libpal.blocks.tile.TileEntityModInventory;

public class SimpleItemStackHandler extends ItemStackHandler {

	private final boolean allowWrite;
	private final TileEntityModInventory tile;

	public SimpleItemStackHandler(TileEntityModInventory inv, boolean allowWrite) {
		super(inv.getSizeInventory());
		this.allowWrite = allowWrite;
		this.tile = inv;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if(allowWrite)
			return super.insertItem(slot, stack, simulate);
		else
			return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if(allowWrite)
			return super.extractItem(slot, amount, simulate);
		else 
			return null;
	}

	@Override
	public void onContentsChanged(int slot) { tile.markDirty(); }
}
