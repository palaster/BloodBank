package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.BBApi;
import palaster.bb.libs.LibNBT;

public class ItemAshenEstusFlask extends ItemModSpecial {
	
	public ItemAshenEstusFlask() {
		super();
		setUnlocalizedName("ashenEstusFlask");
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
			if(!playerIn.isSneaking()) {
				if(itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().getInteger(LibNBT.amount) > 0) {
					BBApi.addFocus(playerIn, 150);
					itemStackIn.getTagCompound().setInteger(LibNBT.amount, itemStackIn.getTagCompound().getInteger(LibNBT.amount) - 1);
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
				}
			} else
				if(itemStackIn.hasTagCompound()) {
					ItemStack estusFlask = new ItemStack(BBItems.estusFlask);
					if(!estusFlask.hasTagCompound())
						estusFlask.setTagCompound(new NBTTagCompound());
					estusFlask.getTagCompound().setInteger(LibNBT.amount, itemStackIn.getTagCompound().getInteger(LibNBT.amount));
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, estusFlask);
				}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
