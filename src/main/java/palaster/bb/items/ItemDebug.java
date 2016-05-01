package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.core.helpers.BBPlayerHelper;

public class ItemDebug extends ItemModSpecial {

	public ItemDebug() {
		super();
		setUnlocalizedName("debug");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			if(!playerIn.isSneaking()) {
				if(BBApi.isUndead(playerIn)) {
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Already undead.");
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Vigor: " + BBApi.getVigor(playerIn));
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Strength: " + BBApi.getStrength(playerIn));
				} else {
					BBApi.setUndead(playerIn, true);
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Now undead.");
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Vigor: " + BBApi.getVigor(playerIn));
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Strength: " + BBApi.getStrength(playerIn));
				}
			} else {
				BBApi.addVigor(playerIn, 1);
				BBApi.addStrength(playerIn, 1);
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
