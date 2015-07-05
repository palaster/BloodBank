package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemModStaff extends ItemModSpecial implements IDuctTapeable {
	
	public String[] powers = new String[] {};

	public ItemModStaff() {
		super();
		setMaxDamage(256);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			tooltip.add(StatCollector.translateToLocal("ss.staff.active") + ": " + StatCollector.translateToLocal(((ItemModStaff) stack.getItem()).powers[getActivePower(stack)]));
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("Active Power", 0);
	}
	
	public static final void setActivePower(ItemStack stack, int value) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(getActiveMax(stack) == 0)
			stack.getTagCompound().setInteger("Active Power", 0);
		else
			stack.getTagCompound().setInteger("Active Power", value);
	}
	
	public static final int getActivePower(ItemStack stack) {
		if(stack.hasTagCompound())
			return stack.getTagCompound().getInteger("Active Power");
		return 0;
	}
	
	public static final int getActiveMax(ItemStack stack) { return ((ItemModStaff) stack.getItem()).powers.length; }
}
