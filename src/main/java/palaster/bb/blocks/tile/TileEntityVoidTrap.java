package palaster.bb.blocks.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityVoidTrap extends TileEntityMod {
	
	public static final String TAG_ORIGINAL_BLOCK_ID = "OriginalBlockID";
	public static final String TAG_INT_TIMER = "VoidTimer";

	private Block ogBlock;
	private final int timerMax = 2400;
	private int timer = 0;
	
	public Block getOriginalBlock() { return ogBlock; }
	
	public void setOriginalBlock(Block value) { this.ogBlock = value; }
	
	@Override
	public void readPacketNBT(NBTTagCompound compound) {
		super.readPacketNBT(compound);
		ogBlock = Block.getBlockById(compound.getInteger(TAG_ORIGINAL_BLOCK_ID));
		timer = compound.getInteger(TAG_INT_TIMER);
	}
	
	@Override
	public void writePacketNBT(NBTTagCompound compound) {
		compound.setInteger(TAG_ORIGINAL_BLOCK_ID, Block.getIdFromBlock(ogBlock));
		compound.setInteger(TAG_INT_TIMER, timer);
		super.writePacketNBT(compound);
	}

	@Override
	public void update() {
		if(!worldObj.isRemote) {
			if(timer >= timerMax)
				worldObj.getBlockState(getPos()).getBlock().breakBlock(worldObj, pos, worldObj.getBlockState(pos));
			else
				timer++;
		}
	}
}
