package palaster97.ss.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemCoreFuel extends ItemModNBT {

	public ItemCoreFuel() {
		super();
		setUnlocalizedName("coreFuel");
	}
	
	@Override
	public boolean hasContainerItem() { return true; }
	
	@Override
	public Item getContainerItem() { return this; }
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack stack = new ItemStack(getContainerItem());
		stack.setTagCompound(new NBTTagCompound());
		if(itemStack.hasTagCompound()) {
			stack.getTagCompound().setString("PlayerUUID", itemStack.getTagCompound().getString("PlayerUUID"));
			return stack;
		}
		return super.getContainerItem(itemStack);
	}
}
