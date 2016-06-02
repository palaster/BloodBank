package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
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
        if(!worldIn.isRemote)
            if(BBApi.isUndead(playerIn)) {
                BBApi.syncServerToClient(playerIn);
                playerIn.openGui(BloodBank.instance, 2, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
                return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
            }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public void receiveButtonEvent(int buttonId, EntityPlayer player) {
        if(BBWorldHelper.findBlockVicinityFromPlayer(BBBlocks.bonfire, player.worldObj, player, 10, 4) != null)
            switch(buttonId) {
                case 0: {
                    if(BBApi.getSoulCostForNextLevel(player) <= BBApi.getSoul(player)) {
                        BBApi.addVigor(player, 1);
                        if(BBApi.getSoulCostForNextLevel(player) > 0)
                            BBApi.setSoul(player, BBApi.getSoul(player) - BBApi.getSoulCostForNextLevel(player));
                    }
                    break;
                }
                case 1: {
                    if(BBApi.getSoulCostForNextLevel(player) <= BBApi.getSoul(player)) {
                        BBApi.addAttunement(player, 1);
                        if(BBApi.getSoulCostForNextLevel(player) > 0)
                            BBApi.setSoul(player, BBApi.getSoul(player) - BBApi.getSoulCostForNextLevel(player));
                    }
                    break;
                }
                case 2: {
                    if(BBApi.getSoulCostForNextLevel(player) <= BBApi.getSoul(player)) {
                        BBApi.addStrength(player, 1);
                        if(BBApi.getSoulCostForNextLevel(player) > 0)
                            BBApi.setSoul(player, BBApi.getSoul(player) - BBApi.getSoulCostForNextLevel(player));
                    }
                    break;
                }
                case 3: {
                    if(BBApi.getSoulCostForNextLevel(player) <= BBApi.getSoul(player)) {
                        BBApi.addIntelligence(player, 1);
                        if(BBApi.getSoulCostForNextLevel(player) > 0)
                            BBApi.setSoul(player, BBApi.getSoul(player) - BBApi.getSoulCostForNextLevel(player));
                    }
                    break;
                }
                case 4: {
                    if(BBApi.getSoulCostForNextLevel(player) <= BBApi.getSoul(player)) {
                        BBApi.addFaith(player, 1);
                        if(BBApi.getSoulCostForNextLevel(player) > 0)
                            BBApi.setSoul(player, BBApi.getSoul(player) - BBApi.getSoulCostForNextLevel(player));
                    }
                    break;
                }
            }
    }
}
