package palaster.bb.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.BBApi;
import palaster.bb.libs.LibNBT;

public class ItemEstusFlask extends ItemModSpecial {
	
	public ItemEstusFlask() {
		super();
		setMaxDamage(1);
		setUnlocalizedName("estusFlask");
		addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            	if(stack.getItemDamage() == 1)
            		return 1.0f;
            	return 0.0f;
            }
        });
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			tooltip.add(I18n.format("bb.misc.amountLeft") + ": " + stack.getTagCompound().getInteger(LibNBT.amount));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			if(itemStackIn.getItemDamage() == 0) {
				if(!playerIn.isSneaking()) {
					if(itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().getInteger(LibNBT.amount) > 0) {
						playerIn.heal(2f);
						itemStackIn.getTagCompound().setInteger(LibNBT.amount, itemStackIn.getTagCompound().getInteger(LibNBT.amount) - 1);
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
					}
				} else
					if(itemStackIn.hasTagCompound()) {
						itemStackIn.damageItem(1, playerIn);
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
					}
			} else {
				if(!playerIn.isSneaking()) {
					if(itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().getInteger(LibNBT.amount) > 0) {
						BBApi.addFocus(playerIn, 150);
						itemStackIn.getTagCompound().setInteger(LibNBT.amount, itemStackIn.getTagCompound().getInteger(LibNBT.amount) - 1);
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
					}
				} else
					if(itemStackIn.hasTagCompound()) {
						itemStackIn.damageItem(-1, playerIn);
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
					}
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) { return false; }
	
	@SuppressWarnings("deprecation")
	@Override
	public String getItemStackDisplayName(ItemStack stack) { return ("" + net.minecraft.util.text.translation.I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + "." + stack.getItemDamage() + ".name")).trim(); }
}
