package palaster.bb.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.libs.LibNBT;

import java.util.UUID;

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
        if(compound.getString(LibNBT.ownerUUID).isEmpty())
            owner = UUID.fromString(compound.getString(LibNBT.ownerUUID));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(owner != null)
            compound.setString(LibNBT.ownerUUID, owner.toString());
        return super.writeToNBT(compound);
    }

    public UUID getOwner() { return owner; }

    public void setOwner(UUID owner) { this.owner = owner; }
}
