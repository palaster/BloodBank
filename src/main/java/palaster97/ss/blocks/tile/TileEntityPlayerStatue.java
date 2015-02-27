package palaster97.ss.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlayerStatue extends TileEntity {

	private String name;
	
	public TileEntityPlayerStatue() {
		name = "&BROKEN&";
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("Name", name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		name = compound.getString("Name");
	}
	
	public String getUUIDName() { return name; }
	
	public void setUUIDName(String name1) {
		if(worldObj.getPlayerEntityByName(name1) != null)
			name = worldObj.getPlayerEntityByName(name1).getUniqueID().toString();
		else
			name = "&BROKEN&";
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(pos, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) { readFromNBT(pkt.getNbtCompound()); }
}