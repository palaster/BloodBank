package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.helpers.SSPlayerHelper;

public abstract class ItemModNBT extends ItemModSpecial {

	public ItemModNBT() { super(); }
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(stack.hasTagCompound()) {
			if(SSPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")) != null)
				tooltip.add(SSPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")).getGameProfile().getName());
			else
				tooltip.add(StatCollector.translateToLocal("ss.misc.brokenPlayer"));
		} else
			tooltip.add(StatCollector.translateToLocal("ss.misc.broken"));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(!itemStackIn.hasTagCompound())
				itemStackIn.setTagCompound(new NBTTagCompound());
			itemStackIn.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
		}
		return itemStackIn;
	}
}
