package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.rpg.RPGCareerBase;
import palaster.libpal.core.helpers.NBTHelper;
import palaster.libpal.items.ItemModSpecial;

public class ItemCareerPamphlet extends ItemModSpecial {
	
	public static final String TAG_STRING_CAREER_CLASS = "CareerPamphletCareerClass";

	public ItemCareerPamphlet(ResourceLocation rl) { super(rl); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound() && !NBTHelper.getStringFromItemStack(stack, TAG_STRING_CAREER_CLASS).isEmpty())
			try {
				tooltip.add(I18n.format("bb.career.base") + ": " + I18n.format(((RPGCareerBase) Class.forName(NBTHelper.getStringFromItemStack(stack, TAG_STRING_CAREER_CLASS)).newInstance()).toString()));
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote)
			if(!NBTHelper.getStringFromItemStack(itemStackIn, TAG_STRING_CAREER_CLASS).isEmpty()) {
				IRPG rpg = RPGCapabilityProvider.get(playerIn);
				if(rpg != null && rpg.getCareer() == null) {
					try {
						rpg.setCareer(playerIn, (RPGCareerBase)Class.forName(NBTHelper.getStringFromItemStack(itemStackIn, TAG_STRING_CAREER_CLASS)).newInstance());
					} catch(Exception e) {
						e.printStackTrace();
					}
					playerIn.setHeldItem(hand, null);
					return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
				}
			}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
