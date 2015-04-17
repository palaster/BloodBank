package palaster97.ss.inventories;

public class InventorySpace extends InventoryMod {
	
	public InventorySpace() { super(54); }
	
	public void copy(InventorySpace inv) {
		for(int i = 0; i < inv.getSizeInventory(); ++i)
			setInventorySlotContents(i, (inv.getStackInSlot(i) == null ? null : inv.getStackInSlot(i).copy()));
		markDirty();
	}

	@Override
	public String getName() { return "container.space"; }
}
