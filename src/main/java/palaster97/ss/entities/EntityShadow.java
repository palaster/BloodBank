package palaster97.ss.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class EntityShadow extends EntityTameable {
	
	private int temp;

	@SuppressWarnings("rawtypes")
	public EntityShadow(World worldIn) {
		super(worldIn);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
        tasks.addTask(2, new EntityAIRestrictSun(this));
        tasks.addTask(2, new EntityAIAvoidEntity(this, new Predicate() {
            public boolean func_179911_a(Entity p_179911_1_) { return p_179911_1_ instanceof EntityCreeper && ((EntityCreeper)p_179911_1_).getCreeperState() > 0; }
            public boolean apply(Object p_apply_1_) { return func_179911_a((Entity)p_apply_1_); } }, 4.0F, 1.0D, 2.0D));
        tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        tasks.addTask(6, new EntityAIWander(this, 1.0D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        setTamed(false);
        setSize(0.4f, 0.7f);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(isEntityInvulnerable(source))
            return false;
        else {
            Entity entity = source.getEntity();
            aiSit.setSitting(false);
            if(entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
                amount = (amount + 1.0F) / 2.0F;
            return super.attackEntityFrom(source, amount);
        }
    }
	
	@Override
    public void onLivingUpdate() {
    	if(!worldObj.isRemote) {
    		if(temp >= 3000) {
    			setDead();
    			temp = 0;
    		} else
    			temp++;
    	}
        if(worldObj.isDaytime() && !worldObj.isRemote) {
            float f = getBrightness(1.0F);
            BlockPos blockpos = new BlockPos(posX, (double)Math.round(posY), posZ);
            if(f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && worldObj.canSeeSky(blockpos)) {
                boolean flag = true;
                ItemStack itemstack = getEquipmentInSlot(4);
                if(itemstack != null) {
                    if(itemstack.isItemStackDamageable()) {
                        itemstack.setItemDamage(itemstack.getItemDamage() + rand.nextInt(2));
                        if(itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                            renderBrokenItemStack(itemstack);
                            setCurrentItemOrArmor(4, (ItemStack)null);
                        }
                    }
                    flag = false;
                }
                if(flag)
                    setFire(8);
            }
        }
        super.onLivingUpdate();
    }
	
    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        temp = tagCompund.getInteger("Temp");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Temp", temp);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() { return EnumCreatureAttribute.UNDEAD; }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) { return null; }
}
