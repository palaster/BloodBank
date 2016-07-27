package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.core.helpers.BBWorldHelper;
import palaster.bb.entities.careers.CareerUndead;

public class ItemUndeadMonitor extends ItemModSpecial {

    public ItemUndeadMonitor() {
        super();
        setUnlocalizedName("undeadMonitor");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 1; }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote) {
        	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
        	if(rpg != null)
        		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead) {
        			BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(playerIn);
                    playerIn.openGui(BloodBank.instance, 2, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
        		}
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public void receiveButtonEvent(int buttonId, EntityPlayer player) {
        if(BBWorldHelper.findBlockVicinityFromPlayer(BBBlocks.bonfire, player.worldObj, player, 10, 4) != null) {
        	final IRPG rpg = RPGCapabilityProvider.get(player);
        	if(rpg != null)
        		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead)
        			switch(buttonId) {
                        case 0: {
                            if(BBApi.getSoulCostForNextLevel(player) <= ((CareerUndead) rpg.getCareer()).getSoul() && ((CareerUndead) rpg.getCareer()).getVigor() < 99) {
                            	((CareerUndead) rpg.getCareer()).setVigor(((CareerUndead) rpg.getCareer()).getVigor() + 1);
                            	BBApi.recalculateVigorBoost(player);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	((CareerUndead) rpg.getCareer()).setSoul(((CareerUndead) rpg.getCareer()).getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 1: {
                            if(BBApi.getSoulCostForNextLevel(player) <= ((CareerUndead) rpg.getCareer()).getSoul() && ((CareerUndead) rpg.getCareer()).getAttunement() < 99) {
                            	((CareerUndead) rpg.getCareer()).setAttunement(((CareerUndead) rpg.getCareer()).getAttunement() + 1);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	((CareerUndead) rpg.getCareer()).setSoul(((CareerUndead) rpg.getCareer()).getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 2: {
                            if(BBApi.getSoulCostForNextLevel(player) <= ((CareerUndead) rpg.getCareer()).getSoul() && ((CareerUndead) rpg.getCareer()).getStrength() < 99) {
                            	((CareerUndead) rpg.getCareer()).setStrength(((CareerUndead) rpg.getCareer()).getStrength() + 1);
                            	BBApi.recalculateStrengthBoost(player);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	((CareerUndead) rpg.getCareer()).setSoul(((CareerUndead) rpg.getCareer()).getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 3: {
                            if(BBApi.getSoulCostForNextLevel(player) <= ((CareerUndead) rpg.getCareer()).getSoul() && ((CareerUndead) rpg.getCareer()).getIntelligence() < 99) {
                            	((CareerUndead) rpg.getCareer()).setIntelligence(((CareerUndead) rpg.getCareer()).getIntelligence() + 1);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	((CareerUndead) rpg.getCareer()).setSoul(((CareerUndead) rpg.getCareer()).getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 4: {
                            if(BBApi.getSoulCostForNextLevel(player) <= ((CareerUndead) rpg.getCareer()).getSoul() && ((CareerUndead) rpg.getCareer()).getFaith() < 99) {
                            	((CareerUndead) rpg.getCareer()).setFaith(((CareerUndead) rpg.getCareer()).getFaith() + 1);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	((CareerUndead) rpg.getCareer()).setSoul(((CareerUndead) rpg.getCareer()).getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
                            }
                            break;
                        }
                    }
        		}
    }
}
