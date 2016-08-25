package palaster.bb.blocks.tile;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCommunityTool extends TileEntityModInventory {
	
	public static final String TAG_UUID = "CommunityToolUUID";

	private UUID owner;
        
    @Override
	public int getSizeInventory() { return 1; }
    
    @Override
    public void readPacketNBT(NBTTagCompound compound) {
    	super.readPacketNBT(compound);
    	UUID uuid = compound.getUniqueId(TAG_UUID);
    	if(uuid != null)
    		owner = uuid;
    }
    
    @Override
    public void writePacketNBT(NBTTagCompound compound) {
    	if(owner != null)
    		compound.setUniqueId(TAG_UUID, owner);
    	super.writePacketNBT(compound);
    }
    
	@Override
	public void update() {}
	
	@Override
	protected SimpleItemStackHandler createItemHandler() {
		return new SimpleItemStackHandler(this, false) {
			@Override
			protected int getStackLimit(int slot, ItemStack stack) { return 1; }
		};
	}

    public UUID getOwner() { return owner; }

    public void setOwner(UUID player) { this.owner = player; }
}
