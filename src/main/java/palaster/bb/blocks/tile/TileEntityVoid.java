package palaster.bb.blocks.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityVoid extends TileEntity implements ITickable {

	private Block ogBlock;
	private final int timerMax = 2400;
	private int timer = 0;
	
	public Block getOriginalBlock() { return ogBlock; }
	
	public void setOriginalBlock(Block value) { this.ogBlock = value; }
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		ogBlock = Block.getBlockById(compound.getInteger("OriginalBlockID"));
		timer = compound.getInteger("Timer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("OriginalBlockID", Block.getIdFromBlock(ogBlock));
		compound.setInteger("Timer", timer);
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
