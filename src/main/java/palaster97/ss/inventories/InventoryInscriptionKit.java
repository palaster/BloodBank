package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemInscriptionKit;
import palaster97.ss.runes.Rune;

public class InventoryInscriptionKit extends InventoryMod {
	
	private final ItemStack invStack;
	
	public InventoryInscriptionKit(ItemStack stack) {
		super(1);
		this.invStack = stack;
		if(!invStack.hasTagCompound())
			invStack.setTagCompound(new NBTTagCompound());
		readFromNBT(invStack.getTagCompound());
	}

	@Override
	public String getName() { return "container.inscriptionKit"; }

	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public void markDirty() {
		super.markDirty();
		writeToNBT(invStack.getTagCompound());
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) { return player.getHeldItem() == invStack; }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) { return !(stack.getItem() instanceof ItemInscriptionKit); }

	@Override
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
