package palaster.bb.blocks.tile;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityModInventory extends TileEntityModBase {

	protected SimpleItemStackHandler itemHandler = createItemHandler();
	
	@Override
	public void readPacketNBT(NBTTagCompound compound) {
		itemHandler = createItemHandler();
		itemHandler.deserializeNBT(compound);
	}
	
	@Override
	public void writePacketNBT(NBTTagCompound compound) { compound.merge(itemHandler.serializeNBT()); }
	
	public abstract int getSizeInventory();

	protected SimpleItemStackHandler createItemHandler() { return new SimpleItemStackHandler(this, true); }

	public IItemHandlerModifiable getItemHandler() { return itemHandler; }

	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, @Nonnull EnumFacing side) { return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side); }
	
	@Nonnull
	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, @Nonnull EnumFacing side) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		return super.getCapability(cap, side);
	}
	
	protected static class SimpleItemStackHandler extends ItemStackHandler {

		private final boolean allowWrite;
		private final TileEntityModInventory tile;

		public SimpleItemStackHandler(TileEntityModInventory inv, boolean allowWrite) {
			super(inv.getSizeInventory());
			this.allowWrite = allowWrite;
			this.tile = inv;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if(allowWrite)
				return super.insertItem(slot, stack, simulate);
			else
				return stack;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if(allowWrite)
				return super.extractItem(slot, amount, simulate);
			else 
				return null;
		}

		@Override
		public void onContentsChanged(int slot) { tile.markDirty(); }
	}
}
