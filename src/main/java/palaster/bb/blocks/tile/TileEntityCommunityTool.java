package palaster.bb.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;

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
        owner = UUID.fromString(compound.getString("OwnerUUID"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(owner != null)
            compound.setString("OwnerUUID", owner.toString());
    }

    public UUID getOwner() { return owner; }

    public void setOwner(UUID owner) { this.owner = owner; }
}
