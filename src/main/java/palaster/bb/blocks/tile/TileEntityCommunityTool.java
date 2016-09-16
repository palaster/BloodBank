package palaster.bb.blocks.tile;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.world.BBWorldSaveData;
import palaster.bb.world.task.Task;

public class TileEntityCommunityTool extends TileEntityModInventory {
	
	public static final String TAG_UUID = "CommunityToolOwner";
	public static final String TAG_UI = "CommunityToolUI";
	public static final String TAG_BOOLEAN = "CommunityToolCommunityTool";

	private UUID owner;
	private List<UUIDItemStack> uis = new LinkedList<UUIDItemStack>();
	
	@Override
	public void update() {
		if(!uis.isEmpty())
			for(UUIDItemStack ui : uis)
				if(ui != null && ui.getUUID() != null) {
					if(ui.getTimer() <= 0) {
						EntityPlayer p = BBPlayerHelper.getPlayerFromDimensions(ui.getUUID());
						if(p != null) {
							if(p.inventory.hasItemStack(ui.getItemStack())) {
								for(int i = 0; i < p.inventory.getSizeInventory(); i++)
									if(p.inventory.getStackInSlot(i) != null)
										if(p.inventory.getStackInSlot(i).hasTagCompound() && p.inventory.getStackInSlot(i).getTagCompound().getBoolean(TAG_BOOLEAN))
											if(ItemStack.areItemsEqualIgnoreDurability(p.inventory.getStackInSlot(i), ui.getItemStack())) {
												p.inventory.setInventorySlotContents(i, null);
												uis.remove(ui);
											}
							} else
								BBWorldSaveData.get(worldObj).addTask(new TaskCTRemoveItemStackFromPlayer(getPos(), ui.uuid, ui.stack));
						} else
							BBWorldSaveData.get(worldObj).addTask(new TaskCTRemoveItemStackFromPlayer(getPos(), ui.uuid, ui.stack));
					} else
						ui.decreaseTimer();
				}
	}

	@Override
	public int getSizeInventory() { return 1; }
	
	@Override
	public void writePacketNBT(NBTTagCompound compound) {
		compound.setUniqueId(TAG_UUID, owner);
		int i = 0;
		if(!uis.isEmpty())
			for(; i < uis.size(); i++)
				if(uis.get(i) != null)
					compound.setTag(TAG_UI + i, uis.get(i).saveNBT());
		compound.setInteger(TAG_UI + "Size", i);
		super.writePacketNBT(compound);
	}
	
	@Override
	public void readPacketNBT(NBTTagCompound compound) {
		owner = compound.getUniqueId(TAG_UUID);
		int temp = compound.getInteger(TAG_UI + "Size");
		for(int i = 0; i < temp; i++) {
			UUIDItemStack ui = new UUIDItemStack();
			ui.loadNBT(compound.getCompoundTag(TAG_UI + i));
			uis.add(ui);
		}
		super.readPacketNBT(compound);
	}
	
	public void setOwner(UUID uuid) { this.owner = uuid; }
	
	public UUID getOwner() { return owner; }
	
	public boolean canAddUUIDItemStack(UUID uuid) {
		if(!uis.isEmpty())
			for(UUIDItemStack ui : uis)
				if(ui != null && ui.getUUID() != null && ui.getUUID().equals(uuid))
					return false;
		return true;
	}
	
	public ItemStack addUUIDItemStack(UUID uuid) {
		ItemStack stack = getItemHandler().getStackInSlot(0).copy();
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean(TAG_BOOLEAN, true);
		uis.add(new UUIDItemStack(uuid, stack, 6000));
		return stack;
	}
	
	public boolean removeUUIDItemStack(UUID uuid, ItemStack stack) {
		if(ItemStack.areItemsEqualIgnoreDurability(getItemHandler().getStackInSlot(0), stack))
			if(!uis.isEmpty())
				for(UUIDItemStack ui : uis)
					if(ui != null && ui.getUUID() != null && ui.getUUID().equals(uuid))
						return uis.remove(ui);
		return false;
	}
	
	public UUIDItemStack getUUIDItemStack(UUID uuid) {
		for(UUIDItemStack ui : uis)
			if(ui != null && ui.getUUID() != null)
				if(ui.getUUID().equals(uuid) && ItemStack.areItemsEqualIgnoreDurability(ui.getItemStack(), getItemHandler().getStackInSlot(0)))
					return ui;
		return null;
	}
	
	public void removeAllItemStack() {
		for(UUIDItemStack ui : uis)
			if(ui != null && ui.getUUID() != null) {
				EntityPlayer p = BBPlayerHelper.getPlayerFromDimensions(ui.getUUID());
				if(p != null) {
					if(p.inventory.hasItemStack(ui.getItemStack())) {
						for(int i = 0; i < p.inventory.getSizeInventory(); i++)
							if(p.inventory.getStackInSlot(i) != null)
								if(p.inventory.getStackInSlot(i).hasTagCompound() && p.inventory.getStackInSlot(i).getTagCompound().getBoolean(TAG_BOOLEAN))
									if(ItemStack.areItemsEqualIgnoreDurability(p.inventory.getStackInSlot(i), ui.getItemStack())) {
										p.inventory.setInventorySlotContents(i, null);
										uis.remove(ui);
									}
					} else
						BBWorldSaveData.get(worldObj).addTask(new TaskCTRemoveItemStackFromPlayer(getPos(), ui.uuid, ui.stack));
				} else
					BBWorldSaveData.get(worldObj).addTask(new TaskCTRemoveItemStackFromPlayer(getPos(), ui.uuid, ui.stack));
			}
	}
	
	public static class UUIDItemStack {
		
		public static final String TAG_UUID = "UUIDItemStackUUID";
		public static final String TAG_ITEMSTACK = "UUIDItemStackItemStack";
		public static final String TAG_INT = "UUIDItemStackTimer";
		
		private UUID uuid;
		private ItemStack stack;
		private int timer;
		
		public UUIDItemStack() {}
		
		public UUIDItemStack(UUID uuid, ItemStack stack) { this(uuid, stack, 6000); }
		
		public UUIDItemStack(UUID uuid, ItemStack stack, int timer) {
			this.uuid = uuid;
			this.stack = stack;
			this.timer = timer;
		}
		
		public UUID getUUID() { return uuid; }
		
		public ItemStack getItemStack() { return stack; }
		
		public int getTimer() { return timer; }
		
		public void decreaseTimer() { timer--; }
		
		public NBTTagCompound saveNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			if(uuid != null)
				nbt.setUniqueId(TAG_UUID, uuid);
			if(stack != null)
				nbt.setTag(TAG_ITEMSTACK, stack.writeToNBT(new NBTTagCompound()));
			nbt.setInteger(TAG_INT, timer);
			return nbt;
		}
		
		public void loadNBT(NBTTagCompound nbt) {
			uuid = nbt.getUniqueId(TAG_UUID);
			stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(TAG_ITEMSTACK));
			timer = nbt.getInteger(TAG_INT);
		}
	}
	
	public static class TaskCTRemoveItemStackFromPlayer extends Task {
		
		public static final String TAG_INT = "CTRIFPDimensionID";
		public static final String TAG_BLOCKPOS = "CTRIFPBlockPos";
		public static final String TAG_UUID = "CTRIFPUUID";
		public static final String TAG_TAG_ITEMSTACK = "CTRIFPItemStack";
		
		private BlockPos pos;
		private UUID uuid;
		private ItemStack itemstack;
		
		public TaskCTRemoveItemStackFromPlayer() {}
		
		public TaskCTRemoveItemStackFromPlayer(BlockPos pos, UUID uuid, ItemStack itemstack) {
			this.pos = pos;
			this.uuid = uuid;
			this.itemstack = itemstack;
		}
		
		@Override
		public void onTick(World world) {
			EntityPlayer p = BBPlayerHelper.getPlayerFromDimensions(uuid);
			if(p != null)
				if(p.inventory.hasItemStack(itemstack))
					for(int i = 0; i < p.inventory.getSizeInventory(); i++)
						if(p.inventory.getStackInSlot(i) != null)
							if(p.inventory.getStackInSlot(i).hasTagCompound() && p.inventory.getStackInSlot(i).getTagCompound().getBoolean(TAG_BOOLEAN)) {
								p.inventory.setInventorySlotContents(i, null);
								super.onTick(world);
							}
		}
		
		public BlockPos getPos() { return pos; }
		
		public UUID getUUID() { return uuid; }
		
		public ItemStack getItemStack() { return itemstack; }
		
		@Override
		public NBTTagCompound saveNBT(NBTTagCompound nbt) {
			nbt.setInteger(TAG_BLOCKPOS + "X", pos.getX());
			nbt.setInteger(TAG_BLOCKPOS + "Y", pos.getY());
			nbt.setInteger(TAG_BLOCKPOS + "Z", pos.getZ());
			nbt.setUniqueId(TAG_UUID, uuid);
			nbt.setTag(TAG_TAG_ITEMSTACK, itemstack.writeToNBT(new NBTTagCompound()));
			return super.saveNBT(nbt);
		}
		
		@Override
		public void loadNBT(NBTTagCompound nbt) {
			pos = new BlockPos(nbt.getInteger(TAG_BLOCKPOS + "X"), nbt.getInteger(TAG_BLOCKPOS + "Y"), nbt.getInteger(TAG_BLOCKPOS + "Z"));
			uuid = nbt.getUniqueId(TAG_UUID);
			ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(TAG_TAG_ITEMSTACK));
			super.loadNBT(nbt);
		}
	}
}