package palaster.bb.entities;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.ai.EntityModAIAttackRangedBow;

import java.util.Calendar;

public class EntitySkeletonMinion extends EntityTameable implements IMob, IRangedAttackMob {

    private int temp;

    private static final DataParameter<Integer> SKELETON_MINION_VARIANT = EntityDataManager.<Integer>createKey(EntitySkeletonMinion.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> field_184728_b = EntityDataManager.<Boolean>createKey(EntitySkeletonMinion.class, DataSerializers.BOOLEAN);
    private final EntityModAIAttackRangedBow aiArrowAttack = new EntityModAIAttackRangedBow(this, 1.0D, 20, 15.0F);
    private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false) {
        public void resetTask() {
            super.resetTask();
            EntitySkeletonMinion.this.func_184724_a(false);
        }

        public void startExecuting() {
            super.startExecuting();
            EntitySkeletonMinion.this.func_184724_a(true);
        }
    };

    public EntitySkeletonMinion(World worldIn) {
        this(worldIn, 0);
    }

    public EntitySkeletonMinion(World worldIn, int value) {
        super(worldIn);
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(2, aiSit);
        tasks.addTask(2, new EntityAIRestrictSun(this));
        tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        tasks.addTask(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        tasks.addTask(6, new EntityAIWander(this, 1.0D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        setTamed(false);
        if (value == 0)
            setSize(0.75F, 2.25F);
        else
            setSize(0.9F, 2.7F);
        if (worldIn != null && !worldIn.isRemote)
            setCombatTask();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.register(SKELETON_MINION_VARIANT, Integer.valueOf(0));
        dataWatcher.register(field_184728_b, Boolean.valueOf(false));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.entity_skeleton_ambient;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.entity_skeleton_hurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.entity_skeleton_death;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) { playSound(SoundEvents.entity_skeleton_step, 0.15F,1.0F); }
    
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
                ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.getPotionById(30), 200));
            return true;
        } else
            return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() { return EnumCreatureAttribute.UNDEAD; }

    @Override
    public void onLivingUpdate() {
        if(worldObj.isDaytime() && !worldObj.isRemote) {
            float f = getBrightness(1.0F);
            BlockPos blockpos = getRidingEntity() instanceof EntityBoat ? (new BlockPos(posX, (double)Math.round(posY), posZ)).up() : new BlockPos(posX, (double)Math.round(posY), posZ);
            if(f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && worldObj.canSeeSky(blockpos)) {
                boolean flag = true;
                ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                if(itemstack != null) {
                    if(itemstack.isItemStackDamageable()) {
                        itemstack.setItemDamage(itemstack.getItemDamage() + rand.nextInt(2));
                        if(itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                            renderBrokenItemStack(itemstack);
                            setItemStackToSlot(EntityEquipmentSlot.HEAD, (ItemStack)null);
                        }
                    }
                    flag = false;
                }
                if (flag)
                    setFire(8);
            }
        }
        if(worldObj.isRemote)
            updateSize(getSkeletonType());
        super.onLivingUpdate();
    }

    @Override
    protected ResourceLocation getLootTable() { return getSkeletonType() == 1 ? LootTableList.ENTITIES_WITHER_SKELETON : LootTableList.ENTITIES_SKELETON; }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.bow));
    }
    
    public void setCombatTask() {
        if(worldObj != null && !worldObj.isRemote) {
            tasks.removeTask(aiAttackOnCollide);
            tasks.removeTask(aiArrowAttack);
            ItemStack itemstack = getHeldItemMainhand();
            if(itemstack != null && itemstack.getItem() == Items.bow)
                tasks.addTask(4, aiArrowAttack);
            else
                tasks.addTask(4, aiAttackOnCollide);
        }
    }

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) { return null; }
	
	@Override
	public boolean isChild() { return true; }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        if(worldObj.provider instanceof WorldProviderHell && getRNG().nextInt(5) > 0) {
            tasks.addTask(4, aiAttackOnCollide);
            setSkeletonType(1);
            setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.stone_sword));
            getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        } else {
            tasks.addTask(4, aiArrowAttack);
            setEquipmentBasedOnDifficulty(difficulty);
            setEnchantmentBasedOnDifficulty(difficulty);
        }
        setCanPickUpLoot(rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());
        if(getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null) {
            Calendar calendar = worldObj.getCurrentDate();
            if(calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F) {
                setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }
        return livingdata;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
        EntityArrow entityarrow = new EntityTippedArrow(worldObj, this);
        double d0 = target.posX - posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityarrow.posY;
        double d2 = target.posZ - posZ;
        double d3 = (double) MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        entityarrow.setThrowableHeading(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - worldObj.getDifficulty().getDifficultyId() * 4));
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.power, this);
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.punch, this);
        entityarrow.setDamage((double)(p_82196_2_ * 2.0F) + rand.nextGaussian() * 0.25D + (double)((float)worldObj.getDifficulty().getDifficultyId() * 0.11F));

        if(i > 0)
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);

        if(j > 0)
            entityarrow.setKnockbackStrength(j);

        if(EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.flame, this) > 0 || getSkeletonType() == 1)
            entityarrow.setFire(100);

        playSound(SoundEvents.entity_skeleton_shoot, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(entityarrow);
    }

    public int getSkeletonType()
    {
        return ((Integer)dataWatcher.get(SKELETON_MINION_VARIANT)).intValue();
    }

    public void setSkeletonType(int p_82201_1_) {
        dataWatcher.set(SKELETON_MINION_VARIANT, Integer.valueOf(p_82201_1_));
        isImmuneToFire = p_82201_1_ == 1;
        updateSize(p_82201_1_);
    }

    private void updateSize(int p_184726_1_) {
        if (p_184726_1_ == 1)
            setSize(0.7F, 2.4F);
        else
            setSize(0.6F, 1.99F);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        if(tagCompund.hasKey("SkeletonType", 99)) {
            int i = tagCompund.getByte("SkeletonType");
            setSkeletonType(i);
        }
        setCombatTask();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("SkeletonType", (byte)getSkeletonType());
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);
        if(!worldObj.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
            setCombatTask();
    }

    @Override
    public float getEyeHeight() { return getSkeletonType() == 1 ? 1.125F : .9F; }
    
    @Override
    public double getYOffset() { return super.getYOffset() - 0.5D; }

    
    @SideOnly(Side.CLIENT)
    public boolean func_184725_db() { return ((Boolean)dataWatcher.get(field_184728_b)).booleanValue(); }

    public void func_184724_a(boolean p_184724_1_) { dataWatcher.set(field_184728_b, Boolean.valueOf(p_184724_1_)); }
}
