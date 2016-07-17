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
import palaster.bb.api.capabilities.entities.BloodBankCapability.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
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
                	final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
					if(bloodBank != null) {
						if(bloodBank.getCurrentBlood() >= 2000) {
							bloodBank.consumeBlood(2000);
	                        player.setHeldItem(hand, new ItemStack(BBItems.bloodBottle));
	                    }
	                    return EnumActionResult.SUCCESS;
					}
                }
            } else {
                if(player.isSneaking()) {
                    setDead();
                    EntityItem bankID = new EntityItem(worldObj, player.posX, player.posY, player.posZ, new ItemStack(BBItems.bbResources, 1, 1));
                    worldObj.spawnEntityInWorld(bankID);
                    return EnumActionResult.SUCCESS;
                } else {
                	final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
					if(bloodBank != null) {
						if(bloodBank.getMaxBlood() <= 0)
	                        BBPlayerHelper.sendChatMessageToPlayer(player, "You do not have an account with this bank.");
	                    if(bloodBank.getMaxBlood() > 0)
	                        BBPlayerHelper.sendChatMessageToPlayer(player, "You current balance is " + bloodBank.getCurrentBlood() + " out of " + bloodBank.getMaxBlood());
					}
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return super.applyPlayerInteraction(player, vec, stack, hand);
    }
}
