package palaster97.ss.blocks.tile;

import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.rituals.Ritual;
import palaster97.ss.rituals.RitualActive;

public class TileEntityRitual extends TileEntityModInventory implements IUpdatePlayerListBox {
	
	public TileEntityRitual() { super(1); }

	private boolean isActive;
	private int activeRitualID = -1;
	private int activeRitaulLength = -1;
	private UUID player = null;

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		if(getStackInSlot(0) != null) {
			if(!worldObj.isRemote)
				worldObj.markBlockForUpdate(pos);
			if(getStackInSlot(0).stackSize <= p_70298_2_) {
				ItemStack itemstack = getStackInSlot(0);
				setInventorySlotContents(0, null);
				markDirty();
				return itemstack;
			}
			ItemStack itemstack = getStackInSlot(0).splitStack(p_70298_2_);
			if(getStackInSlot(0).stackSize == 0) setInventorySlotContents(0, null); 
			markDirty();
			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if(getStackInSlot(0) != null) {
			ItemStack itemstack = getStackInSlot(0);
			setInventorySlotContents(0, null);
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		super.setInventorySlotContents(0, p_70299_2_);
		if(worldObj != null && !worldObj.isRemote)
			worldObj.markBlockForUpdate(pos);
	}

	@Override
	public int getInventoryStackLimit() { return 1; }
	
	@Override
	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
		readCustomNBT(p_145839_1_);
	}
	
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
		byte b0 = nbttagcompound1.getByte("Slot");
		if(b0 >= 0)
			setInventorySlotContents(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
		isActive = nbttagcompound.getBoolean("IsActive");
		activeRitualID = nbttagcompound.getInteger("ActiveRitualID");
		activeRitaulLength = nbttagcompound.getInteger("ACtiveRitualLength");
		if(nbttagcompound.getString("PlayerUUID").equals(null) || nbttagcompound.getString("PlayerUUID").equals(""))
			player = null;
		else
			player = UUID.fromString(nbttagcompound.getString("PlayerUUID"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound p_145841_1_) {
		super.writeToNBT(p_145841_1_);
		writeCustomNBT(p_145841_1_);
	}
	
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		NBTTagList nbttaglist = new NBTTagList();
		if(getStackInSlot(0) != null) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setByte("Slot", (byte)0);
			getStackInSlot(0).writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}
		nbttagcompound.setTag("Items", nbttaglist);
		nbttagcompound.setBoolean("IsActive", isActive);
		nbttagcompound.setInteger("ActiveRitualID", activeRitualID);
		nbttagcompound.setInteger("ActiveRitualLength", activeRitaulLength);
		if(player != null)
			nbttagcompound.setString("PlayerUUID", player.toString());
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(pos, -999, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readCustomNBT(pkt.getNbtCompound());
	}

	@Override
	public String getName() { return "container.ritual"; }
	
	public void setRitualID(int id, EntityPlayer player) {
		isActive = true;
		activeRitualID = id;
		activeRitaulLength = ((RitualActive) Ritual.rituals[activeRitualID]).length;
		this.player = player.getUniqueID();
		setInventorySlotContents(0, new ItemStack(Blocks.bedrock, 1, 0));
	}
	
	public int getRitualID() { return activeRitualID; }
	
	public void setRitualLength(int length) { activeRitaulLength = length; }
	
	public boolean getIsActive() { return isActive; }

	@Override
	public void update() {
		if(!worldObj.isRemote) {
			if(isActive) {
				if(activeRitaulLength == 0) {
					if(worldObj.getPlayerEntityByUUID(player) != null) {
						EntityPlayer p = worldObj.getPlayerEntityByUUID(player);
						SoulNetworkExtendedPlayer props = SoulNetworkExtendedPlayer.get(p);
						if(props != null) {
							if(Ritual.rituals[activeRitualID] != null) {
								if(Ritual.rituals[activeRitualID] instanceof RitualActive) {
									RitualActive ritual = (RitualActive) Ritual.rituals[activeRitualID];
									if(p != null) {
										ritual.activate(worldObj, pos, p);
										SSPlayerHelper.sendChatMessageToPlayer(p, "Your Ritual Has Finished.");
										props.removeRitual(activeRitualID, pos);
										setInventorySlotContents(0, null);
										isActive = false;
										IBlockState block = worldObj.getBlockState(pos);
										block.getBlock().setHardness(0);
									}
								}
							}
						}
					}
					activeRitaulLength = -1;
				} else if(activeRitaulLength > 0)
					activeRitaulLength--;
			}
		}
		markDirty();
	}
}
