package palaster.bb.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import palaster.bb.libs.LibResource;

public class EntityItztiliTablet extends EntityCreature {
	
	public static String tag_timer = "ItztiliTimer";
	public static String tag_number = "ItztiliEnemyCount";

    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS));
    private int enemyCount;
    private int delay;

    public EntityItztiliTablet(World world) {
        super(world);
        setHealth(getMaxHealth());
        isImmuneToFire = true;
        targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    public void onLivingUpdate() {
        if(!worldObj.isRemote) {
        	if(getAttackTarget() != null && getAttackTarget() instanceof EntityPlayer) {
                if(delay == 0) {
                        for(int i = 0; i < 4; i++) {
                            if(enemyCount < getMaxHealth()) {
	                            EntityPigZombie pigZombie = new EntityPigZombie(worldObj);
	                            pigZombie.setPosition(posX + worldObj.rand.nextInt(4), posY + .25, posZ + worldObj.rand.nextInt(4));
	                            pigZombie.setAttackTarget(getAttackTarget());
	                            worldObj.spawnEntityInWorld(pigZombie);
	                            enemyCount++;
                            } else
                            	attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
                        }
                        delay = 50;
                    } else
                    	delay--;
                bossInfo.setPercent(( (float) ((int) getMaxHealth() - enemyCount) / getMaxHealth()));
        	}
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected ResourceLocation getLootTable() { return LibResource.itztiliTabletLoot; }
    
    @Override
    public void knockBack(Entity entityIn, float strenght, double xRatio, double zRatio) {}

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger(tag_number, enemyCount);
        tagCompound.setInteger(tag_timer, delay);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        enemyCount = tagCompund.getInteger(tag_number);
        delay = tagCompund.getInteger(tag_timer);
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public boolean isNonBoss() { return false; }
}
