package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.capabilities.items.IVampiric;
import palaster.bb.libs.LibNBT;

public abstract class ItemModStaff extends ItemModSpecial implements IVampiric {
	
	public String[] powers = new String[] {};

	public ItemModStaff() {
		super();
		setMaxDamage(256);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			tooltip.add(I18n.format("bb.staff.active") + ": " + I18n.format(((ItemModStaff) stack.getItem()).powers[getActivePower(stack)]));
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger(LibNBT.activePower, 0);
	}
	
	public static final void setActivePower(ItemStack stack, int value) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(getActiveMax(stack) == 0)
			stack.getTagCompound().setInteger(LibNBT.activePower, 0);
		else
			stack.getTagCompound().setInteger(LibNBT.activePower, value);
	}
	
	public static final int getActivePower(ItemStack stack) {
		if(stack.hasTagCompound())
			return stack.getTagCompound().getInteger(LibNBT.activePower);
		return 0;
	}
	
	public static final int getActiveMax(ItemStack stack) { return ((ItemModStaff) stack.getItem()).powers.length; }
}
