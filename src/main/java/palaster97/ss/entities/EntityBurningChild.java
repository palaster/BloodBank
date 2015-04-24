package palaster97.ss.entities;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;

public class EntityBurningChild extends EntityMob {
	
	private boolean isActive;
	private String target;
	private int finalEmbrace;

	public EntityBurningChild(World worldIn) {
		super(worldIn);
		((PathNavigateGround)getNavigator()).func_179688_b(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        tasks.addTask(2, field_175455_a);
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        tasks.addTask(7, new EntityAIWander(this, 1.0D));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        setSize(0.6F, 1.95F);
	}
	
	@Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.5D);
    }
	
	@Override
	protected boolean canDespawn() { return target == null || target.isEmpty(); }
	
	@Override
	protected String getLivingSound() { return "mob.zombie.say"; }

	@Override
    protected String getHurtSound() { return "mob.zombie.hurt"; }

	@Override
    protected String getDeathSound() { return "mob.zombie.death"; }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) { this.playSound("mob.zombie.step", 0.15F, 1.0F); }
	
	@Override
	public boolean isChild() { return true; }
	
	@Override
    public void onLivingUpdate() {
        if(worldObj.isDaytime() && !worldObj.isRemote && !isChild()) {
            BlockPos blockpos = new BlockPos(posX, (double)Math.round(posY), posZ);
            if(getBrightness(1.0F) > 0.5F && rand.nextFloat() * 30.0F < (getBrightness(1.0F) - 0.4F) * 2.0F && worldObj.canSeeSky(blockpos))
            	setActive(false);
        }
        if(isRiding() && getAttackTarget() != null && ridingEntity instanceof EntityChicken)
            ((EntityLiving)ridingEntity).getNavigator().setPath(getNavigator().getPath(), 1.5D);
        super.onLivingUpdate();
    }
	
	@Override
	public void onDeath(DamageSource cause) {
		if(!worldObj.isRemote)
			if(target != null || !target.isEmpty()) {
				if(worldObj.getPlayerEntityByUUID(UUID.fromString(target)) != null) {
					EntityPlayer p = worldObj.getPlayerEntityByUUID(UUID.fromString(target));
					SoulNetworkExtendedPlayer ex = SoulNetworkExtendedPlayer.get(p);
					if(p != null && ex != null) {
						if(ex.getBurningChild() != null && ex.getBurningChild() == getEntityData()) {
							ex.setBurningChild(null);
							EntityBurningChild bc = new EntityBurningChild(worldObj);
							bc.readEntityFromNBT(getEntityData());
							if(p.getBedLocation() != null) {
								bc.setPosition(p.getBedLocation().getX(), p.getBedLocation().getY(), p.getBedLocation().getZ());
								bc.setActive(false);
								worldObj.spawnEntityInWorld(bc);
							}
						}
					}
				}
			}
		super.onDeath(cause);
	}
	
	@Override
	protected boolean interact(EntityPlayer player) {
		if(!worldObj.isRemote)
			if(target != null && !target.isEmpty()) {
				if(SoulNetworkExtendedPlayer.get(player) != null && SoulNetworkExtendedPlayer.get(player).getBurningChild() != null)
					if(worldObj.getPlayerEntityByUUID(UUID.fromString(target)) != null && worldObj.getPlayerEntityByUUID(UUID.fromString(target)).equals(player)) {
						if(finalEmbrace >= 3) {
							SoulNetworkExtendedPlayer.get(player).setBurningChild(null);
							setDead();
						} else
							finalEmbrace++;
						return true;
					}
			}
		return false;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		isActive = tagCompund.getBoolean("IsActive");
		target = tagCompund.getString("Target");
		finalEmbrace = tagCompund.getInteger("FinalEmbrace");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("IsActive", isActive);
		if(target != null && !target.isEmpty())
			tagCompound.setString("Target", target);
		tagCompound.setInteger("FinalEmbrace", finalEmbrace);
	}
	
	public boolean isActive() { return isActive; }
	
	public void setActive(boolean isActive) { this.isActive = isActive; }
	
	public void clearTarget() { target = null; }
	
	public void clearEmbrace() { finalEmbrace = 0; }
	
	@Override
	public void setAttackTarget(EntityLivingBase p_70624_1_) {
		if(!worldObj.isRemote) {
			if(p_70624_1_ != null) {
				if(p_70624_1_ instanceof EntityPlayer) {
					EntityPlayer p = (EntityPlayer) p_70624_1_;
					if(target != null && !target.isEmpty())
						if(!isActive && worldObj.getPlayerEntityByUUID(UUID.fromString(target)) != null && worldObj.getPlayerEntityByUUID(UUID.fromString(target)).equals(p))
							isActive = true;
					if(target == null || target.isEmpty())
						if(SoulNetworkExtendedPlayer.get(p) != null && SoulNetworkExtendedPlayer.get(p).getBurningChild() == null) {
							SoulNetworkExtendedPlayer.get(p).setBurningChild(getEntityData());
							isActive = true;
							target = p.getUniqueID().toString();
							super.setAttackTarget(p);
						}
				}
			} else
				if(isActive) {
					isActive = false;
					clearEmbrace();
					super.setAttackTarget(p_70624_1_);
				}
		}
	}
}
