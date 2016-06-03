package palaster.bb.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import palaster.bb.libs.LibNBT;

import javax.annotation.Nullable;

public abstract class TileEntityModInventory extends TileEntity implements IInventory {
	
	private ItemStack[] items;
	
	public TileEntityModInventory(int length) {
		super();
		items = new ItemStack[length];
	}

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public ITextComponent getDisplayName() { return null; }

	@Override
	public int getSizeInventory() { return items.length; }

	@Override
	@Nullable
	public ItemStack getStackInSlot(int slotIn) { return items[slotIn]; }

	@Override
	@Nullable
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
	@Nullable
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		items[index] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer playerIn) { return playerIn.getDistanceSq(pos) <= 64; }

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
	public void clear() {
		for(int i = 0; i < items.length; i++)
			items[i] = null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList items = compound.getTagList(LibNBT.items, compound.getId());
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			int slot = item.getByte(LibNBT.slot);
			if(slot >= 0 && slot < getSizeInventory())
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList items = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++) {		
			ItemStack stack = getStackInSlot(i);	
			if(stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger(LibNBT.slot, i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag(LibNBT.items, items);
		return super.writeToNBT(compound);
	}
	
	public void receiveButtonEvent(int buttonID, EntityPlayer player) {}
}
