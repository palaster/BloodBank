package palaster.bb.compat.jei;

import javax.annotation.Nonnull;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import palaster.bb.compat.jei.blood.BloodRecipeCategory;
import palaster.bb.compat.jei.blood.BloodRecipeHandler;

@JEIPlugin
public class JEIBloodBankPlugin implements IModPlugin {

	@Override
	public void register(@Nonnull IModRegistry registry) {
		registry.addRecipeCategories(new BloodRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new BloodRecipeHandler());
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
		recipeTransferRegistry.addRecipeTransferHandler(ContainerWorkbench.class, BloodRecipeCategory.CATEGORY_UID, 1, 9, 10, 36);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.CRAFTING_TABLE), BloodRecipeCategory.CATEGORY_UID);
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {}
}
