package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapability.UndeadCapabilityProvider;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.core.helpers.BBWorldHelper;

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
        	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
        	if(undead != null)
        		if(undead.isUndead()) {
        			BloodBank.proxy.syncPlayerUndeadCapabilitiesToClient(playerIn);
                    playerIn.openGui(BloodBank.instance, 2, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
        		}
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public void receiveButtonEvent(int buttonId, EntityPlayer player) {
        if(BBWorldHelper.findBlockVicinityFromPlayer(BBBlocks.bonfire, player.worldObj, player, 10, 4) != null) {
        	final IUndead undead = UndeadCapabilityProvider.get(player);
        	if(undead != null)
        		if(undead.isUndead())
        			switch(buttonId) {
                        case 0: {
                            if(BBApi.getSoulCostForNextLevel(player) <= undead.getSoul() && undead.getVigor() < 99) {
                            	undead.setVigor(undead.getVigor() + 1);
                            	BBApi.recalculateVigorBoost(player);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	undead.setSoul(undead.getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerUndeadCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 1: {
                            if(BBApi.getSoulCostForNextLevel(player) <= undead.getSoul() && undead.getAttunement() < 99) {
                            	undead.setAttunement(undead.getAttunement() + 1);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	undead.setSoul(undead.getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerUndeadCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 2: {
                            if(BBApi.getSoulCostForNextLevel(player) <= undead.getSoul() && undead.getStrength() < 99) {
                            	undead.setStrength(undead.getStrength() + 1);
                            	BBApi.recalculateStrengthBoost(player);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	undead.setSoul(undead.getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerUndeadCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 3: {
                            if(BBApi.getSoulCostForNextLevel(player) <= undead.getSoul() && undead.getIntelligence() < 99) {
                            	undead.setIntelligence(undead.getIntelligence() + 1);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	undead.setSoul(undead.getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerUndeadCapabilitiesToClient(player);
                            }
                            break;
                        }
                        case 4: {
                            if(BBApi.getSoulCostForNextLevel(player) <= undead.getSoul() && undead.getFaith() < 99) {
                            	undead.setFaith(undead.getFaith() + 1);
                                if(BBApi.getSoulCostForNextLevel(player) > 0)
                                	undead.setSoul(undead.getSoul() - BBApi.getSoulCostForNextLevel(player));
                                BloodBank.proxy.syncPlayerUndeadCapabilitiesToClient(player);
                            }
                            break;
                        }
                    }
        		}
    }
}
