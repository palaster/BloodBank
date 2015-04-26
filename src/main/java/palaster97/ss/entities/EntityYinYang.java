package palaster97.ss.entities;

import palaster97.ss.blocks.SSBlocks;
import palaster97.ss.blocks.tile.TileEntityVoid;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityYinYang extends EntityThrowable {

	public EntityYinYang(World worldIn) {
		super(worldIn);
	}
	
	public EntityYinYang(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}
	
	public EntityYinYang(World worldIn, double x, double y, double p_i1778_6_) {
		super(worldIn, x, y, p_i1778_6_);
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		if(p_70184_1_ != null && p_70184_1_.typeOfHit == MovingObjectType.ENTITY && p_70184_1_.entityHit != null)
			if(p_70184_1_.entityHit instanceof EntityLivingBase)
				((EntityLivingBase) p_70184_1_.entityHit).attackEntityFrom(DamageSource.outOfWorld, 3f);
		if(p_70184_1_ != null && p_70184_1_.typeOfHit == MovingObjectType.BLOCK) {
			for(int i = -1; i < 1 + 1; i++)
				for(int k = -1; k < 1 + 1; k++) {
					BlockPos newPos = new BlockPos(p_70184_1_.getBlockPos().getX() + i, p_70184_1_.getBlockPos().getY(), p_70184_1_.getBlockPos().getZ() + k);
					if(!worldObj.isRemote)
						if(worldObj.getBlockState(newPos) != null && !(worldObj.getBlockState(newPos).getBlock() instanceof BlockContainer)) {
							if(worldObj.getTileEntity(newPos) == null) {
								IBlockState ogBlockState = worldObj.getBlockState(newPos);
								worldObj.setBlockState(newPos, SSBlocks.touchVoid.getDefaultState());
								if(worldObj.getTileEntity(newPos) != null && worldObj.getTileEntity(newPos) instanceof TileEntityVoid)
									((TileEntityVoid) worldObj.getTileEntity(newPos)).setOriginalBlock(ogBlockState.getBlock());
							}
						}
				}
		}
		if(!worldObj.isRemote)
			setDead();
	}
}
