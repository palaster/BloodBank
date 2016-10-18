package palaster.bb.core.helpers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

	public static ItemStack giveNBTTagCompound(ItemStack stack) {
		stack.setTagCompound(new NBTTagCompound());
		return stack;
	}
	
	public static ItemStack setIntegerToItemStack(ItemStack stack, String key, int value) {
		if(!stack.hasTagCompound())
			stack = giveNBTTagCompound(stack);
		stack.getTagCompound().setInteger(key, value);
		return stack;
	}
	
	public static ItemStack setBooleanToItemStack(ItemStack stack, String key, boolean value) {
		if(!stack.hasTagCompound())
			stack = giveNBTTagCompound(stack);
		stack.getTagCompound().setBoolean(key, value);
		return stack;
	}
	
	public static ItemStack setStringToItemStack(ItemStack stack, String key, String value) {
		if(!stack.hasTagCompound())
			stack = giveNBTTagCompound(stack);
		stack.getTagCompound().setString(key, value);
		return stack;
	}
	
	public static ItemStack setUUIDToItemStack(ItemStack stack, String key, @Nullable UUID value) {
		if(!stack.hasTagCompound())
			stack = giveNBTTagCompound(stack);
		if(value != null)
			stack.getTagCompound().setUniqueId(key, value);
		return stack;
	}
	
	public static int getIntegerFromItemStack(ItemStack stack, String key) {
		if(stack.hasTagCompound())
			if(stack.getTagCompound().hasKey(key))
				return stack.getTagCompound().getInteger(key);
		return -1;
	}
	
	public static boolean getBooleanFromItemStack(ItemStack stack, String key) {
		if(stack.hasTagCompound())
			if(stack.getTagCompound().hasKey(key))
				return stack.getTagCompound().getBoolean(key);
		return false;
	}
	
	public static String getStringFromItemStack(ItemStack stack, String key) {
		if(stack.hasTagCompound())
			if(stack.getTagCompound().hasKey(key))
				return stack.getTagCompound().getString(key);
		return "";
	}
	
	@Nullable
	public static UUID getUUIDFromItemStack(ItemStack stack, String key) {
		if(stack.hasTagCompound())
			if(stack.getTagCompound().hasKey(key))
				return stack.getTagCompound().getUniqueId(key);
		return null;
	}
}
