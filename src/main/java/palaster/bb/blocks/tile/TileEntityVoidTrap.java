package palaster.bb.blocks.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityVoidTrap extends TileEntityMod {
	
	public static String tag_originalBlockID = "OriginalBlockID";
	public static String tag_timer = "VoidTimer";

	private Block ogBlock;
	private final int timerMax = 2400;
	private int timer = 0;
	
	public Block getOriginalBlock() { return ogBlock; }
	
	public void setOriginalBlock(Block value) { this.ogBlock = value; }
	
	@Override
	public void readPacketNBT(NBTTagCompound compound) {
		super.readPacketNBT(compound);
		ogBlock = Block.getBlockById(compound.getInteger(tag_originalBlockID));
		timer = compound.getInteger(tag_timer);
	}
	
	@Override
	public void writePacketNBT(NBTTagCompound compound) {
		compound.setInteger(tag_originalBlockID, Block.getIdFromBlock(ogBlock));
		compound.setInteger(tag_timer, timer);
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
