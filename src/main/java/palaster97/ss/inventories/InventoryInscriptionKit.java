package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IChatComponent;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemInscriptionKit;
import palaster97.ss.runes.Rune;

public class InventoryInscriptionKit implements IInventory {
	
	private final ItemStack invStack;
	private ItemStack stack;
	
	public InventoryInscriptionKit(ItemStack stack) {
		this.invStack = stack;
		if(!invStack.hasTagCompound())
			invStack.setTagCompound(new NBTTagCompound());
		readFromNBT(invStack.getTagCompound());
	}

	@Override
	public String getName() { return "inscriptionKit"; }

	@Override
	public boolean hasCustomName() { return false; }

	@Override
	public IChatComponent getDisplayName() { return null; }

	@Override
	public int getSizeInventory() { return 1; }

	@Override
	public ItemStack getStackInSlot(int index) { return stack; }

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
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.stack = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public void markDirty() {
		if(getStackInSlot(0) != null && getStackInSlot(0).stackSize == 0)
			stack = null;
		writeToNBT(invStack.getTagCompound());
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) { return player.getHeldItem() == invStack; }

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) { return !(stack.getItem() instanceof ItemInscriptionKit); }
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList("Inventory", compound.getId());
		for(int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if(slot >= 0 && slot < getSizeInventory())
				stack = ItemStack.loadItemStackFromNBT(item);
		}
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
		compound.setTag("Inventory", items);
	}

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() { stack = null; }

	public void receiveButtonEvent(int id, EntityPlayer player) {
		if(!player.worldObj.isRemote)
			if(getStackInSlot(0) != null)
				if(SoulNetworkExtendedPlayer.get(player) != null && SoulNetworkExtendedPlayer.get(player).getRune() == null)
					for(int i = 0; i < Rune.runes.length; i++)
						if(Rune.runes[i] != null && Rune.runes[i].id.getItem() == getStackInSlot(0).getItem()) {
							SoulNetworkExtendedPlayer.get(player).setRune(Rune.runes[i].runeID);
							setInventorySlotContents(0, null);
						}
	}
}
