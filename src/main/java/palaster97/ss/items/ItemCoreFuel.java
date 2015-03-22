package palaster97.ss.items;

import net.minecraft.item.Item;

public class ItemCoreFuel extends ItemModSpecialNBT {

	public ItemCoreFuel() {
		super();
		setUnlocalizedName("coreFuel");
	}
	
	@Override
	public boolean hasContainerItem() { return true; }
	
	@Override
	public Item getContainerItem() { return this; }
}
