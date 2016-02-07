package palaster.bb.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityBeaver extends EntityTameable {

    public EntityBeaver(World world) {
        super(world);
        setSize(0.75F, 1.3F);
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(2, aiSit);
        tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
        tasks.addTask(4, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        tasks.addTask(5, new EntityAIMate(this, 1.0D));
        tasks.addTask(6, new EntityAIWander(this, 1.0D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        setTamed(false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.275D);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) { return null; }
}
