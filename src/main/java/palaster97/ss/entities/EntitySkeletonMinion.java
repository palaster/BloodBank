package palaster97.ss.entities;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
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
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import palaster97.ss.libs.LibDataWatcher;

import com.google.common.base.Predicate;

public class EntitySkeletonMinion extends EntityTameable implements IMob, IRangedAttackMob {
	
	private int temp;
	
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
    
    public EntitySkeletonMinion(World worldIn) { this(worldIn, 0); }

	@SuppressWarnings("rawtypes")
	public EntitySkeletonMinion(World worldIn, int value) {
		super(worldIn);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
        tasks.addTask(2, new EntityAIRestrictSun(this));
        tasks.addTask(2, new EntityAIAvoidEntity(this, new Predicate() {
            public boolean func_179911_a(Entity p_179911_1_) { return p_179911_1_ instanceof EntityCreeper && ((EntityCreeper)p_179911_1_).getCreeperState() > 0; }
            public boolean apply(Object p_apply_1_) { return func_179911_a((Entity)p_apply_1_); } }, 4.0F, 1.0D, 2.0D));
        tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate() {
            public boolean func_179945_a(Entity p_179945_1_) { return p_179945_1_ instanceof EntityWolf; }
            public boolean apply(Object p_apply_1_) { return func_179945_a((Entity)p_apply_1_); } }, 6.0F, 1.0D, 1.2D));
        tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        tasks.addTask(6, new EntityAIWander(this, 1.0D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        setTamed(false);
        if(value == 0)
        	setSize(0.75F, 2.25F);
        else
        	setSize(0.9F, 2.7F);
        if(worldIn != null && !worldIn.isRemote) 
        	setCombatTask();
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
	}
	
	@Override
	protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(LibDataWatcher.skeleton_minion, new Byte((byte)0));
    }

	@Override
    protected String getLivingSound() { return "mob.skeleton.say"; }

    @Override
    protected String getHurtSound() { return "mob.skeleton.hurt"; }

    @Override
    protected String getDeathSound() { return "mob.skeleton.death"; }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) { playSound("mob.skeleton.step", 0.15F, 1.0F); }
    
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
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        if(super.attackEntityAsMob(p_70652_1_)) {
            if(getSkeletonType() == 1 && p_70652_1_ instanceof EntityLivingBase)
                ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            return true;
        } else
            return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() { return EnumCreatureAttribute.UNDEAD; }
    
    @Override
    public void onLivingUpdate() {
    	if(!worldObj.isRemote) {
    		if(temp >= 6000) {
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
        if(worldObj.isRemote && getSkeletonType() == 1)
        	setSize(0.9F, 2.7F);
        super.onLivingUpdate();
    }

    @Override
    protected void func_180481_a(DifficultyInstance p_180481_1_) {
        super.func_180481_a(p_180481_1_);
        setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }
    
    public void setCombatTask() {
    	tasks.removeTask(aiAttackOnCollide);
        tasks.removeTask(aiArrowAttack);
        ItemStack itemstack = getHeldItem();
        if(itemstack != null && itemstack.getItem() == Items.bow) 
        	tasks.addTask(4, aiArrowAttack);
        else
            tasks.addTask(4, aiAttackOnCollide);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) { return null; }
	
	@Override
	public boolean isChild() { return true; }
	
	public IEntityLivingData skeletonSpawning(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2, int value) {
		p_180482_2 = func_180482_a(p_180482_1_, p_180482_2);
        if(value == 1 && getRNG().nextInt(5) > 0) {
            tasks.addTask(4, aiAttackOnCollide);
            setSkeletonType(1);
            setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
        } else {
            tasks.addTask(4, aiArrowAttack);
            setSkeletonType(0);
            func_180481_a(p_180482_1_);
            func_180483_b(p_180482_1_);
        }
        setCanPickUpLoot(rand.nextFloat() < 0.55F * p_180482_1_.getClampedAdditionalDifficulty());
        if(getEquipmentInSlot(4) == null) {
            Calendar calendar = worldObj.getCurrentDate();
            if(calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F) {
                setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                equipmentDropChances[4] = 0.0F;
            }
        }
		return p_180482_2;
	}
	
	@Override
	public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
		p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
        if(worldObj.provider instanceof WorldProviderHell && getRNG().nextInt(5) > 0) {
            tasks.addTask(4, aiAttackOnCollide);
            setSkeletonType(1);
            setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
        } else {
            tasks.addTask(4, aiArrowAttack);
            func_180481_a(p_180482_1_);
            func_180483_b(p_180482_1_);
        }
        setCanPickUpLoot(rand.nextFloat() < 0.55F * p_180482_1_.getClampedAdditionalDifficulty());
        if(getEquipmentInSlot(4) == null) {
            Calendar calendar = worldObj.getCurrentDate();
            if(calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F) {
                setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                equipmentDropChances[4] = 0.0F;
            }
        }
        return p_180482_2_;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
		EntityArrow entityarrow = new EntityArrow(worldObj, this, p_82196_1_, 1.6F, (float)(14 - worldObj.getDifficulty().getDifficultyId() * 4));
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
        entityarrow.setDamage((double)(p_82196_2_ * 2.0F) + rand.nextGaussian() * 0.25D + (double)((float)worldObj.getDifficulty().getDifficultyId() * 0.11F));
        if(i > 0)
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        if(j > 0)
            entityarrow.setKnockbackStrength(j);
        if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0 || getSkeletonType() == 1)
            entityarrow.setFire(100);
        playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(entityarrow);
	}
	
    public int getSkeletonType() { return dataWatcher.getWatchableObjectByte(LibDataWatcher.skeleton_minion); }

    public void setSkeletonType(int p_82201_1_) {
        dataWatcher.updateObject(LibDataWatcher.skeleton_minion, Byte.valueOf((byte)p_82201_1_));
        isImmuneToFire = p_82201_1_ == 1;
        if(p_82201_1_ == 1)
        	setSize(0.9F, 2.7F);
        else
        	setSize(0.75F, 2.25F);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        if(tagCompund.hasKey("SkeletonMinionType", 99)) {
            byte b0 = tagCompund.getByte("SkeletonMinionType");
            setSkeletonType(b0);
        }
        temp = tagCompund.getInteger("Temp");
        setCombatTask();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("SkeletonMinionType", (byte)getSkeletonType());
        tagCompound.setInteger("Temp", temp);
    }

    @Override
    public void setCurrentItemOrArmor(int slotIn, ItemStack itemStackIn) {
        super.setCurrentItemOrArmor(slotIn, itemStackIn);
        if(!worldObj.isRemote && slotIn == 0)
            setCombatTask();
    }

    @Override
    public float getEyeHeight() { return getSkeletonType() == 1 ? 1.125F : .9F; }
    
    @Override
    public double getYOffset() { return super.getYOffset() - 0.5D; }
}
