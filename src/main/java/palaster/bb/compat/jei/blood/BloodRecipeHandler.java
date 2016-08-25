package palaster.bb.compat.jei.blood;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import palaster.bb.api.recipes.ShapedBloodRecipes;

public class BloodRecipeHandler implements IRecipeHandler<ShapedBloodRecipes> {

	@Override
	public Class<ShapedBloodRecipes> getRecipeClass() { return ShapedBloodRecipes.class; }

	@Override
	public String getRecipeCategoryUid() { return BloodRecipeCategory.CATEGORY_UID; }

	@Nonnull
	@Override
	public String getRecipeCategoryUid(ShapedBloodRecipes recipe) { return BloodRecipeCategory.CATEGORY_UID; }

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull ShapedBloodRecipes recipe) { return new BloodRecipeWrapper(recipe); }

	@Override
	public boolean isRecipeValid(ShapedBloodRecipes recipe) { return true; }
}
