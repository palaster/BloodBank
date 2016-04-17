package palaster.bb.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import palaster.bb.items.BBItems;

public class VampireSigilRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World worldIn) {
		ItemStack tool = null;
		boolean foundHeart = false;
		for(int i = 0; i < p_77569_1_.getSizeInventory(); i++) {
			ItemStack stack = p_77569_1_.getStackInSlot(i);
			if(stack != null)
				if(stack.getItem().isRepairable() && !(stack.hasTagCompound() && stack.getTagCompound().getBoolean("HasVampireSigil")))
					tool = stack;
				else if(stack.isItemEqual(new ItemStack(BBItems.bbResources, 1, 3)))
					foundHeart = true;
		}
		for(int i = 0; i < p_77569_1_.getSizeInventory(); i++) {
			ItemStack stack = p_77569_1_.getStackInSlot(i);
			if(stack != null)
				if(stack != tool && !stack.isItemEqual(new ItemStack(BBItems.bbResources, 1, 3)))
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
		toolCopy.getTagCompound().setBoolean("HasVampireSigil", true);
		return toolCopy;
	}
	
	@Override
	public int getRecipeSize() { return 10; }

	@Override
	public ItemStack getRecipeOutput() { return null; }

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting p_179532_1_) { return ForgeHooks.defaultRecipeGetRemainingItems(p_179532_1_); }
}
