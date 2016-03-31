package palaster.bb.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.helpers.BBPlayerHelper;

import java.util.List;

public class ItemPlayerBinder extends ItemModSpecial {

	public ItemPlayerBinder() {
		super();
		setUnlocalizedName("playerBinder");
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(stack.hasTagCompound()) {
			if(BBPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")) != null)
				tooltip.add(BBPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")).getGameProfile().getName());
			else
				tooltip.add(I18n.translateToLocal("bb.misc.brokenPlayer"));
		} else
			tooltip.add(I18n.translateToLocal("bb.misc.broken"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			if(!itemStackIn.hasTagCompound())
				itemStackIn.setTagCompound(new NBTTagCompound());
			itemStackIn.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
			return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.worldObj.isRemote && target instanceof EntityPlayer)
			if(playerIn.isSneaking()) {
				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setString("PlayerUUID", target.getUniqueID().toString());
				return true;
			}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
}
