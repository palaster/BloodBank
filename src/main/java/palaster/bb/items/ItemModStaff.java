package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.libpal.items.ItemModSpecial;

public abstract class ItemModStaff extends ItemModSpecial {
	
	public static String tag_number = "StaffPower";
	public String[] powers = new String[] {};

	public ItemModStaff(ResourceLocation rl) { this(rl, 256); }
	
	public ItemModStaff(ResourceLocation rl, int maxDamage) { super(rl, maxDamage); }

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
		stack.getTagCompound().setInteger(tag_number, 0);
	}
	
	public static final void setActivePower(ItemStack stack, int value) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(getActiveMax(stack) == 0)
			stack.getTagCompound().setInteger(tag_number, 0);
		else
			stack.getTagCompound().setInteger(tag_number, value);
	}
	
	public static final int getActivePower(ItemStack stack) {
		if(stack.hasTagCompound())
			return stack.getTagCompound().getInteger(tag_number);
		return 0;
	}
	
	public static final int getActiveMax(ItemStack stack) { return ((ItemModStaff) stack.getItem()).powers.length; }
}
