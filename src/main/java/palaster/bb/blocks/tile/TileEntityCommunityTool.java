package palaster.bb.blocks.tile;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.libs.LibNBT;

public class TileEntityCommunityTool extends TileEntityModInventory {

	private UUID owner;

    public TileEntityCommunityTool() { super(1); }

    @Override
    public int getInventoryStackLimit() { return 1; }

    @Override
    public String getName() { return "container.communityTool"; }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        UUID uuid = compound.getUniqueId(LibNBT.uuid);
    	if(uuid != null)
    		owner = uuid;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	compound.setUniqueId(LibNBT.uuid, owner);
        return super.writeToNBT(compound);
    }

    public UUID getOwner() { return owner; }

    public void setOwner(UUID player) { this.owner = player; }
}
