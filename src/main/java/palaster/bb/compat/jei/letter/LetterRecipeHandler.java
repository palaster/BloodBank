package palaster.bb.compat.jei.letter;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import palaster.bb.api.recipes.RecipeLetter;

public class LetterRecipeHandler implements IRecipeHandler<RecipeLetter> {

    @Nonnull
    @Override
    public Class<RecipeLetter> getRecipeClass() { return RecipeLetter.class; }
    
    @Override
    public String getRecipeCategoryUid() { return "bb.letter"; }
    
    @Nonnull
	@Override
	public String getRecipeCategoryUid(RecipeLetter recipe) { return "bb.letter"; }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull RecipeLetter recipe) { return new LetterRecipeWrapper(recipe); }

    @Override
    public boolean isRecipeValid(@Nonnull RecipeLetter recipe) { return true; }
}
