package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.rpg.RPGCareerBase;
import palaster.bb.core.helpers.NBTHelper;

public class ItemCareerPamphlet extends ItemModSpecial {
	
	public static final String TAG_STRING_CAREER_CLASS = "CareerPamphletCareerClass";

	public ItemCareerPamphlet(String unlocalizedName) { super(unlocalizedName); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound() && !NBTHelper.getStringFromItemStack(stack, TAG_STRING_CAREER_CLASS).isEmpty())
			try {
				tooltip.add(I18n.format("bb.career.base") + ": " + I18n.format(((RPGCareerBase) Class.forName(NBTHelper.getStringFromItemStack(stack, TAG_STRING_CAREER_CLASS)).newInstance()).getUnlocalizedName()));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getCareer() == null) {
				try {
					rpg.setCareer((RPGCareerBase)Class.forName(NBTHelper.getStringFromItemStack(itemStackIn, TAG_STRING_CAREER_CLASS)).newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return ActionResult.newResult(EnumActionResult.SUCCESS, null);
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
