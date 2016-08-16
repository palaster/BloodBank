package palaster.bb.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.core.helpers.BBPlayerHelper;
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
        			BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.undead.soul") + ": " + ((CareerUndead) rpg.getCareer()).getSoul());
        			BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.undead.focus") + ": " + ((CareerUndead) rpg.getCareer()).getFocus() + " / " + ((CareerUndead) rpg.getCareer()).getFocusMax());
                    return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
        		}
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
