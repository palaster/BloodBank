package palaster.bb.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

public class InventoryMod implements IInventory {
	
	private ItemStack[] items;
	
	public InventoryMod(int length) { items = new ItemStack[length]; }

	@Override
	public String getName() { return null; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public ITextComponent getDisplayName() { return null; }

	@Override
	public int getSizeInventory() { return items.length; }

	@Override
	public ItemStack getStackInSlot(int index) { return items[index]; }

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		if(stack != null) {
			if(stack.stackSize > count) {
				stack = stack.splitStack(count);
				markDirty();
			} else
				setInventorySlotContents(index, null);
		}
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		items[index] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public void markDirty() {
		for(int i = 0; i < getSizeInventory(); ++i)
			if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
				items[i] = null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) { return true; }

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() {
		for(int i = 0; i < items.length; i++)
			items[i] = null;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList("Items", compound.getId());
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			if(slot >= 0 && slot < getSizeInventory())
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
		}
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList items = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++) {		
			ItemStack stack = getStackInSlot(i);	
			if(stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag("Items", items);
	}
	
	public void receiveButtonEvent(int buttonID, EntityPlayer player) {}
}
