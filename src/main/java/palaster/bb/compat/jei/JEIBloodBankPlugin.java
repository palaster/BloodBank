package palaster.bb.compat.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import palaster.bb.api.BBApi;
import palaster.bb.compat.jei.letter.LetterRecipeCategory;
import palaster.bb.compat.jei.letter.LetterRecipeHandler;

import javax.annotation.Nonnull;

@JEIPlugin
public class JEIBloodBankPlugin implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.addRecipeCategories(new LetterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeHandlers(new LetterRecipeHandler());
        registry.addRecipes(BBApi.letterRecipes);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {}
}
