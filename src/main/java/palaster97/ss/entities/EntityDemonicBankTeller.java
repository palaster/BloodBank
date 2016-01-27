package palaster97.ss.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.extended.SSExtendedPlayer;
import palaster97.ss.items.SSItems;

public class EntityDemonicBankTeller extends EntityLiving {

    public EntityDemonicBankTeller(World world) {
        super(world);
        tasks.addTask(0, new EntityAISwimming(this));
        setSize(0.6F, 1.95F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        if(!worldObj.isRemote)
            if(player.isSneaking()) {
                setDead();
                EntityItem bankID = new EntityItem(worldObj, player.posX, player.posY, player.posZ, new ItemStack(SSItems.ssResources, 1, 1));
                worldObj.spawnEntityInWorld(bankID);
            } else if(SSExtendedPlayer.get(player) != null) {
                if(SSExtendedPlayer.get(player).getBloodMax() <= 0)
                    SSPlayerHelper.sendChatMessageToPlayer(player, "You do not have an account with this bank.");
                if(SSExtendedPlayer.get(player).getBloodMax() > 0)
                    SSPlayerHelper.sendChatMessageToPlayer(player, "You current balance is " + SSExtendedPlayer.get(player).getCurrentBlood() + " out of " + SSExtendedPlayer.get(player).getBloodMax());
                return true;
            }
        return false;
    }
}
