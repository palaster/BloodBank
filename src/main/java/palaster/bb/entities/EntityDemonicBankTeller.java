package palaster.bb.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.items.BBItems;

public class EntityDemonicBankTeller extends EntityLiving {

    public EntityDemonicBankTeller(World world) {
        super(world);
        tasks.addTask(0, new EntityAISwimming(this));
        setSize(0.6F, 1.95F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand) {
        if(!worldObj.isRemote && hand == EnumHand.MAIN_HAND) {
            if(player.getHeldItemMainhand() != null) {
                if(player.getHeldItemMainhand().getItem() instanceof ItemGlassBottle) {
                    if(BBApi.getCurrentBlood(player) >= 2000) {
                        BBApi.consumeBlood(player, 2000);
                        player.setHeldItem(hand, new ItemStack(BBItems.bloodBottle));
                    }
                    return EnumActionResult.SUCCESS;
                }
            } else {
                if(player.isSneaking()) {
                    setDead();
                    EntityItem bankID = new EntityItem(worldObj, player.posX, player.posY, player.posZ, new ItemStack(BBItems.bbResources, 1, 1));
                    worldObj.spawnEntityInWorld(bankID);
                    return EnumActionResult.SUCCESS;
                } else {
                    if(BBApi.getMaxBlood(player) <= 0)
                        BBPlayerHelper.sendChatMessageToPlayer(player, "You do not have an account with this bank.");
                    if(BBApi.getMaxBlood(player) > 0)
                        BBPlayerHelper.sendChatMessageToPlayer(player, "You current balance is " + BBApi.getCurrentBlood(player) + " out of " + BBApi.getMaxBlood(player));
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return super.applyPlayerInteraction(player, vec, stack, hand);
    }
}
