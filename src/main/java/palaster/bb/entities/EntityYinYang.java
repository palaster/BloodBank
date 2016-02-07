package palaster.bb.entities;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityVoid;

public class EntityYinYang extends EntityThrowable {
	
	private int isSneaking = -1;

	public EntityYinYang(World worldIn) {
		super(worldIn);
	}
	
	public EntityYinYang(World worldIn, EntityLivingBase throwerIn, int value) {
		this(worldIn, throwerIn);
		isSneaking = value;
	}
	
	public EntityYinYang(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}
	
	public EntityYinYang(World worldIn, double x, double y, double p_i1778_6_) {
		super(worldIn, x, y, p_i1778_6_);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		isSneaking = tagCompund.getInteger("IsSneaking");
		super.readEntityFromNBT(tagCompund);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("IsSneaking", isSneaking);
		super.writeEntityToNBT(tagCompound);
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		if(p_70184_1_ != null && p_70184_1_.typeOfHit == MovingObjectType.ENTITY && p_70184_1_.entityHit != null) {
			if(isSneaking == 0) {
				if(p_70184_1_.entityHit instanceof EntityLivingBase)
					p_70184_1_.entityHit.attackEntityFrom(DamageSource.outOfWorld, 3f);
			} else if(isSneaking == 1)
				if(p_70184_1_.entityHit instanceof EntityLivingBase)
					p_70184_1_.entityHit.setPosition(getThrower().posX, getThrower().posY, getThrower().posZ);
		}
		if(p_70184_1_ != null && p_70184_1_.typeOfHit == MovingObjectType.BLOCK) {
			for(int i = -1; i < 1 + 1; i++)
				for(int k = -1; k < 1 + 1; k++) {
					BlockPos newPos = new BlockPos(p_70184_1_.getBlockPos().getX() + i, p_70184_1_.getBlockPos().getY(), p_70184_1_.getBlockPos().getZ() + k);
					if(!worldObj.isRemote)
						if(worldObj.getBlockState(newPos) != null && !(worldObj.getBlockState(newPos).getBlock() instanceof BlockContainer)) {
							if(worldObj.getTileEntity(newPos) == null) {
								IBlockState ogBlockState = worldObj.getBlockState(newPos);
								worldObj.setBlockState(newPos, BBBlocks.touchVoid.getDefaultState());
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
