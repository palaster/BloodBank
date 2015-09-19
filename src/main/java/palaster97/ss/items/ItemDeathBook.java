package palaster97.ss.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import palaster97.ss.ScreamingSouls;

public class ItemDeathBook extends ItemModSpecial {

    public ItemDeathBook() {
        super();
        setUnlocalizedName("deathBook");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        if(!playerIn.worldObj.isRemote) {
            EntityZombie zombie = new EntityZombie(playerIn.worldObj);
            // Changing AI
            zombie.tasks.removeTask(new EntityAIAttackOnCollide(zombie, EntityPlayer.class, 1.0D, false));
            zombie.tasks.removeTask(new EntityAIAttackOnCollide(zombie, EntityVillager.class, 1.0D, true));
            zombie.tasks.removeTask(new EntityAIAttackOnCollide(zombie, EntityIronGolem.class, 1.0D, true));
            zombie.targetTasks.removeTask(new EntityAINearestAttackableTarget(zombie, EntityPlayer.class, true));
            zombie.targetTasks.removeTask(new EntityAINearestAttackableTarget(zombie, EntityVillager.class, true));
            zombie.targetTasks.removeTask(new EntityAINearestAttackableTarget(zombie, EntityIronGolem.class, true));
            zombie.tasks.addTask(2, new EntityAIAttackOnCollide(zombie, EntityLivingBase.class, 1.0D, false));
            //
            zombie.setPosition(playerIn.posX + 1, playerIn.posY + .05, playerIn.posZ);
            zombie.setAttackTarget(target);
            PotionEffect speed = new PotionEffect(1, 1200, 3, false, false);
            PotionEffect strength = new PotionEffect(5, 1200, 3, false, false);
            // TODO: Potion Effect Death not working
            if(ScreamingSouls.proxy.death != null) {
                PotionEffect death = new PotionEffect(ScreamingSouls.proxy.death.getId(), 200, 1, false, true);
                zombie.addPotionEffect(death);
            }
            zombie.addPotionEffect(speed);
            zombie.addPotionEffect(strength);
            playerIn.worldObj.spawnEntityInWorld(zombie);
            return true;
        }
        return false;
    }
}
