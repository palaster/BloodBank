package palaster.bb.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityItztiliTablet extends EntityCreature {

    // TODO: Scale boss bar to amount left in temp, create a spawn mechanic, and give it cool drop.

    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS));
    private int temp;
    private int delay;

    public EntityItztiliTablet(World world) {
        super(world);
        isImmuneToFire = true;
        targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
    }

    @Override
    public void onLivingUpdate() {
        if(!worldObj.isRemote) {
            int temp1 = 30 - temp;
            bossInfo.setPercent(temp1 / 30);
            if(getAttackTarget() != null && getAttackTarget() instanceof EntityPlayer) {
                if(delay == 0) {
                    if(temp < 30) {
                        int rand = worldObj.rand.nextInt(3);
                        for(int i = 0; i < 4; i++) {
                            if(rand == 0) {
                                EntityPigZombie pigZombie = new EntityPigZombie(worldObj);
                                pigZombie.setPosition(posX + worldObj.rand.nextInt(4), posY + .25, posZ + worldObj.rand.nextInt(4));
                                pigZombie.setAttackTarget(getAttackTarget());
                                worldObj.spawnEntityInWorld(pigZombie);
                                temp++;
                            } else if(rand == 1) {
                                EntityPigZombie pigZombie = new EntityPigZombie(worldObj);
                                pigZombie.setPosition(posX + worldObj.rand.nextInt(4), posY + .25, posZ + worldObj.rand.nextInt(4));
                                pigZombie.setAttackTarget(getAttackTarget());
                                worldObj.spawnEntityInWorld(pigZombie);
                                temp++;
                            } else if(rand == 2) {
                                EntityPigZombie pigZombie = new EntityPigZombie(worldObj);
                                pigZombie.setPosition(posX + worldObj.rand.nextInt(4), posY + .25, posZ + worldObj.rand.nextInt(4));
                                pigZombie.setAttackTarget(getAttackTarget());
                                worldObj.spawnEntityInWorld(pigZombie);
                                temp++;
                            }
                        }
                    } else
                        setDead();
                    delay = 50;
                } else
                    delay--;
            }
        }
        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.MAX_VALUE);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Temp", temp);
        tagCompound.setInteger("Delay", delay);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        temp = tagCompund.getInteger("Temp");
        delay = tagCompund.getInteger("Delay");
    }

    @Override
    public void setBossVisibleTo(EntityPlayerMP player) {
        super.setBossVisibleTo(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void setBossNonVisibleTo(EntityPlayerMP player) {
        super.setBossNonVisibleTo(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public boolean isNonBoss() { return false; }
}
