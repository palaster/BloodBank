package palaster97.ss.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class TapeHeartRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World worldIn) { return false; }

	@Override
	public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) { return null; }
	
	@Override
	public int getRecipeSize() { return 0; }

	@Override
	public ItemStack getRecipeOutput() { return null; }

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting p_179532_1_) { return null; }
}
