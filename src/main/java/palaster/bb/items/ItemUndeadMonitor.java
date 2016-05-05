package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;

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
                playerIn.openGui(BloodBank.instance, 2, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
                return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
            }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public void receiveButtonEvent(int buttonId, EntityPlayer player) {
        switch(buttonId) {
            case 0: {
                System.out.println("Vigor Increase");
                BBApi.addVigor(player, 1);
                break;
            }
            case 1: {
                System.out.println("Attunement Increase");
                BBApi.addAttunement(player, 1);
                break;
            }
            case 2: {
                System.out.println("Strength Increase");
                BBApi.addStrength(player, 1);
                break;
            }
            case 3: {
                System.out.println("Intelligence Increase");
                BBApi.addIntelligence(player, 1);
                break;
            }
            case 4: {
                System.out.println("Faith Increase");
                BBApi.addFaith(player, 1);
                break;
            }
        }
    }
}
