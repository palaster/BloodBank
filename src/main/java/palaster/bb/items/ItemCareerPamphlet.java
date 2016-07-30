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

public class ItemCareerPamphlet extends ItemModSpecial {

	public ItemCareerPamphlet() {
		super();
		setUnlocalizedName("careerPamphlet");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null) {
				if(rpg.getCareer() != null)
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.career.base") + " : " + I18n.format(rpg.getCareer().getUnlocalizedName()));
				else
					BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.career.nocareer"));
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
