package palaster.bb.entities;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityVoid;

public class EntityYinYang extends EntityThrowable {
	
	public static String tag_number = "YinYangSneaking";

	private int isSneaking = -1;

	public EntityYinYang(World worldIn) {
		super(worldIn);
	}

	public EntityYinYang(World worldIn, EntityLivingBase throwerIn, int value) {
		super(worldIn, throwerIn);
		isSneaking = value;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		isSneaking = tagCompund.getInteger(tag_number);
		super.readEntityFromNBT(tagCompund);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger(tag_number, isSneaking);
		super.writeEntityToNBT(tagCompound);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if(result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit != null) {
			if(isSneaking == 0) {
				if(result.entityHit instanceof EntityLivingBase)
					result.entityHit.attackEntityFrom(DamageSource.outOfWorld, 8f);
			} else if(isSneaking == 1)
				if(result.entityHit instanceof EntityLivingBase)
					result.entityHit.setPosition(getThrower().posX, getThrower().posY, getThrower().posZ);
		}
		if(result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			for(int i = -1; i < 1 + 1; i++)
				for(int k = -1; k < 1 + 1; k++) {
					BlockPos newPos = new BlockPos(result.getBlockPos().getX() + i, result.getBlockPos().getY(), result.getBlockPos().getZ() + k);
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
