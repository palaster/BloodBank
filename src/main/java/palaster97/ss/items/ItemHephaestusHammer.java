package palaster97.ss.items;

import net.minecraft.item.ItemStack;

public class ItemHephaestusHammer extends ItemModSpecial implements IDuctTapeable {
	
	public ItemHephaestusHammer() {
		super();
		setMaxDamage(256);
		setUnlocalizedName("hephaestusHammer");
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if(itemStack != null && itemStack.getItemDamage() < getMaxDamage()) {
			itemStack.setItemDamage(itemStack.getItemDamage() + 1);
			return itemStack;
		}
		return null;
	}
}
