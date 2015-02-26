package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IChatComponent;

public class InventorySpace implements IInventory {
	
	private ItemStack[] items = new ItemStack[54];

	@Override
	public int getSizeInventory() { return items.length; }

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) { return items[p_70301_1_]; }

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		ItemStack stack = getStackInSlot(p_70298_1_);
		if(stack != null) {
			if(stack.stackSize > p_70298_2_) {
				stack = stack.splitStack(p_70298_2_);
				markDirty();
			} else
				setInventorySlotContents(p_70298_1_, null);
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		ItemStack stack = getStackInSlot(p_70304_1_);
		setInventorySlotContents(p_70304_1_, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		items[p_70299_1_] = p_70299_2_;
		if(p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
			p_70299_2_.stackSize = getInventoryStackLimit();
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public void markDirty() {
		for(int i = 0; i < getSizeInventory(); ++i) {
			if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
				items[i] = null;
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) { return true; }
	
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) { return true; }
	
	public void copy(InventorySpace inv) {
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			items[i] = (stack == null ? null : stack.copy());
		}
		markDirty();
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList items = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); ++i) {
			if(getStackInSlot(i) != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag("Items", items);
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList("Items", compound.getId());
		for(int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if(slot >= 0 && slot < getSizeInventory())
				this.items[slot] = ItemStack.loadItemStackFromNBT(item);
		}
	}

	@Override
	public String getName() { return "container.space"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public IChatComponent getDisplayName() { return null; }

	@Override
	public void openInventory(EntityPlayer playerIn) {}

	@Override
	public void closeInventory(EntityPlayer playerIn) {}
	
	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clearInventory() {
		for(int i = 0; i < items.length; i++)
			items[i] = null;
	}
}
