package palaster.bb.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import palaster.bb.items.BBItems;

public class EntityTalisman extends EntityThrowable {
	
	public static final String TAG_INT_TYPE = "TalismanType";
	
	private int talismanType = 0;

	public EntityTalisman(World worldIn) {
		super(worldIn);
	}
	
	public EntityTalisman(World worldIn, EntityLivingBase throwerIn, int value) {
		super(worldIn, throwerIn);
		talismanType = value;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		talismanType = tagCompund.getInteger(TAG_INT_TYPE);
		super.readEntityFromNBT(tagCompund);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger(TAG_INT_TYPE, talismanType);
		super.writeEntityToNBT(tagCompound);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if(result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit != null) {
			if(result.entityHit instanceof EntityLivingBase)
				switch(talismanType) {
					case 1: {
						((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 0, false, true));
						break;
					}
					case 2: {
						((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, 1200, 0, false, true));
						break;
					}
				}
		} else if(result != null && result.typeOfHit == RayTraceResult.Type.BLOCK)
			if(!worldObj.isRemote) {
				BlockPos pos = result.getBlockPos();
				switch(result.sideHit) {
					case DOWN: {
						pos.down();
						break;
					}
					case UP: {
						pos.up();
						break;
					}
					case NORTH: {
						pos.north();
						break;
					}
					case SOUTH: {
						pos.south();
						break;
					}
					case WEST: {
						pos.west();
						break;
					}
					case EAST: {
						pos.east();
						break;
					}
				}
				EntityItem talisman = new EntityItem(worldObj, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BBItems.talisman, 1, talismanType));
				worldObj.spawnEntityInWorld(talisman);
			}
		if(!worldObj.isRemote)
			setDead();
	}
}
