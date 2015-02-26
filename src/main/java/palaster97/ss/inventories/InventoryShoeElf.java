package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IChatComponent;

public class InventoryShoeElf implements IInventory {
	
	private ItemStack product;
	private ItemStack[] inv = new ItemStack[9];

	@Override
	public String getName() { return "container.shoeElf"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public IChatComponent getDisplayName() { return null; }

	@Override
	public int getSizeInventory() { return inv.length; }

	@Override
	public ItemStack getStackInSlot(int slotIn) { return inv[slotIn]; }

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = getStackInSlot(index);
		if(itemstack != null) {
			if(itemstack.stackSize <= count)
				setInventorySlotContents(index, null);
			else {
				itemstack = itemstack.splitStack(count);
				markDirty();
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = inv[index].copy();
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inv[index] = stack;
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
				inv[i] = null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer playerIn) { return true; }

	@Override
	public void openInventory(EntityPlayer playerIn) {}

	@Override
	public void closeInventory(EntityPlayer playerIn) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clearInventory() {}
	
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
		
		NBTTagList product = new NBTTagList();
		ItemStack stack = getProduct();
		if(stack != null) {
			NBTTagCompound item = new NBTTagCompound();
			stack.writeToNBT(item);
			items.appendTag(item);
		}
		compound.setTag("Product", product);
	}

	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList("Items", compound.getId());
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound)items.get(i);
			int slot = item.getByte("Slot");
			if(slot >= 0 && slot < getSizeInventory())
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
		}
		setProduct(ItemStack.loadItemStackFromNBT((NBTTagCompound)(compound.getTagList("Product", compound.getId())).get(0)));
	}
	
	public ItemStack getProduct() { return product; }
	
	public void setProduct(ItemStack value) { product = value; }
}
