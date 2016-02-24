package palaster.bb.compat.jei;

import mezz.jei.api.*;
import palaster.bb.api.BBApi;
import palaster.bb.compat.jei.letter.LetterRecipeCategory;
import palaster.bb.compat.jei.letter.LetterRecipeHandler;

import javax.annotation.Nonnull;

@JEIPlugin
public class JEIBloodBankPlugin implements IModPlugin {

    @Deprecated
    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {}

    @Deprecated
    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {}

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.addRecipeCategories(new LetterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeHandlers(new LetterRecipeHandler());
        registry.addRecipes(BBApi.letterRecipes);
    }

    @Deprecated
    @Override
    public void onRecipeRegistryAvailable(@Nonnull IRecipeRegistry recipeRegistry) {}

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {}
}
