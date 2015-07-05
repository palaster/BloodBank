package palaster97.ss.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import palaster97.ss.items.SSItems;

public class TapeHeartRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World worldIn) {
		ItemStack tool = null;
		boolean foundHeart = false;
		for(int i = 0; i < p_77569_1_.getSizeInventory(); i++) {
			ItemStack stack = p_77569_1_.getStackInSlot(i);
			if(stack != null)
				if(stack.getItem().isRepairable() && !(stack.hasTagCompound() && stack.getTagCompound().getBoolean("HasTapeHeart")))
					tool = stack;
				else if(stack.getItem() == SSItems.tapeHeart)
					foundHeart = true;
		}
		for(int i = 0; i < p_77569_1_.getSizeInventory(); i++) {
			ItemStack stack = p_77569_1_.getStackInSlot(i);
			if(stack != null)
				if(stack != tool && stack.getItem() != SSItems.tapeHeart)
					return false;
		}
		return tool != null && foundHeart;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
		ItemStack tool = null;
		for(int i = 0; i < p_77572_1_.getSizeInventory(); i++)
			if(p_77572_1_.getStackInSlot(i) != null && p_77572_1_.getStackInSlot(i).getItem().isDamageable())
				tool = p_77572_1_.getStackInSlot(i);
		if(tool == null)
			return null;
		ItemStack toolCopy = tool.copy();
		if(!toolCopy.hasTagCompound())
			toolCopy.setTagCompound(new NBTTagCompound());
		toolCopy.getTagCompound().setBoolean("HasTapeHeart", true);
		return toolCopy;
	}
	
	@Override
	public int getRecipeSize() { return 10; }

	@Override
	public ItemStack getRecipeOutput() { return null; }

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting p_179532_1_) {
		ItemStack[] aitemstack = new ItemStack[p_179532_1_.getSizeInventory()];
		for(int i = 0; i < aitemstack.length; ++i)
	        aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(p_179532_1_.getStackInSlot(i));
		return aitemstack;
    }
}
