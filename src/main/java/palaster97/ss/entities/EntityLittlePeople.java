package palaster97.ss.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityLittlePeople extends EntityTameable {

	public EntityLittlePeople(World worldIn) {
		super(worldIn);
		setSize(0.55F, 0.95F);
        ((PathNavigateGround)getNavigator()).func_179690_a(true);
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(2, aiSit);
        tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        tasks.addTask(7, new EntityAIWander(this, 1.0D));
        tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(9, new EntityAILookIdle(this));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6D);
	}
	
	@Override
	public boolean interact(EntityPlayer p_70085_1_) {
		if(!worldObj.isRemote) {
			aiSit.setSitting(!isSitting());
	        isJumping = false;
	        navigator.clearPathEntity();
	        setAttackTarget((EntityLivingBase)null);
		}
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) { return null; }
}
