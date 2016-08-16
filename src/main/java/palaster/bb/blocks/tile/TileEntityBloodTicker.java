package palaster.bb.blocks.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;

public class TileEntityBloodTicker extends TileEntityMod {
	
	@Override
	public void update() {
		if(!worldObj.isRemote)
			if(worldObj.rand.nextInt(8) == 0) {
				IBlockState bs = worldObj.getBlockState(getPos().up());
				if(bs != null && bs.getBlock() != null) {
					ITickable tick = (ITickable) worldObj.getTileEntity(getPos().up());
					if(tick != null)
						tick.update();
					else if(bs.getBlock().getTickRandomly())
						bs.getBlock().updateTick(worldObj, getPos().up(), bs, worldObj.rand);
				}
		}
	}	
}
