package palaster.bb.world.chunk;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

public class WorkshopChunkWrapper {
	
	public static final String TAG_INT_DIM = "WorkshopChunkDimensionID", 
	TAG_UUID_OWNER = "WorkshopChunkOwner",
	TAG_INT_CHUNK = "WorkshopChunkPos";
	
	private int dimID;
	private UUID owner;
	private Chunk chunk;
	
	public WorkshopChunkWrapper() {}
	
	public WorkshopChunkWrapper(int dimID, UUID owner, Chunk chunk) {
		this.dimID = dimID;
		this.owner = owner;
		this.chunk = chunk;
	}
	
	public int getDimensionID() { return dimID; }
	
	public UUID getOwner() { return owner; }
	
	public Chunk getChunk() { return chunk; }

	public NBTTagCompound saveNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(TAG_INT_DIM, dimID);
		nbt.setUniqueId(TAG_UUID_OWNER, owner);
		nbt.setInteger(TAG_INT_CHUNK + "X", chunk.xPosition);
		nbt.setInteger(TAG_INT_CHUNK + "Z", chunk.zPosition);
		return nbt;
	}

	public void loadNBT(NBTTagCompound nbt) {
		dimID = nbt.getInteger(TAG_INT_DIM);
		if(nbt.hasKey(TAG_UUID_OWNER))
			owner = nbt.getUniqueId(TAG_UUID_OWNER);
		if(DimensionManager.getWorld(dimID) != null)
			chunk = DimensionManager.getWorld(dimID).getChunkFromChunkCoords(nbt.getInteger(TAG_INT_CHUNK + "X"), nbt.getInteger(TAG_INT_CHUNK + "Z"));
		
	}
}
