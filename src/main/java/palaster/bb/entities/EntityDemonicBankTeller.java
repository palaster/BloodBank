package palaster.bb.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster.bb.items.BBItems;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.extended.BBExtendedPlayer;

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
                EntityItem bankID = new EntityItem(worldObj, player.posX, player.posY, player.posZ, new ItemStack(BBItems.ssResources, 1, 1));
                worldObj.spawnEntityInWorld(bankID);
            } else if(BBExtendedPlayer.get(player) != null) {
                if(BBExtendedPlayer.get(player).getBloodMax() <= 0)
                    BBPlayerHelper.sendChatMessageToPlayer(player, "You do not have an account with this bank.");
                if(BBExtendedPlayer.get(player).getBloodMax() > 0)
                    BBPlayerHelper.sendChatMessageToPlayer(player, "You current balance is " + BBExtendedPlayer.get(player).getCurrentBlood() + " out of " + BBExtendedPlayer.get(player).getBloodMax());
                return true;
            }
        return false;
    }
}
