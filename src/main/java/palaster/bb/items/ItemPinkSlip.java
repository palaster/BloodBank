package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.core.proxy.CommonProxy;
import palaster.libpal.items.ItemModSpecial;

public class ItemPinkSlip extends ItemModSpecial {

	public ItemPinkSlip(ResourceLocation rl) { super(rl); }
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote) {
        	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
    		if(rpg != null)
    			if(rpg.getCareer() != null) {
    				rpg.setCareer(playerIn, null);
    				CommonProxy.syncPlayerRPGCapabilitiesToClient(playerIn);
    				BBPlayerHelper.sendChatMessageToPlayer(playerIn, net.minecraft.util.text.translation.I18n.translateToLocal("bb.career.fired"));
    				playerIn.setHeldItem(hand, null);
    				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
        		}
    	}
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
