package palaster.bb.compat.jei.letter;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import palaster.bb.items.BBItems;
import palaster.bb.libs.LibResource;

import javax.annotation.Nonnull;
import java.util.Collection;

public class LetterRecipeCategory implements IRecipeCategory {

    private final IDrawable background;
    private final IDrawable row;
    private final IDrawable arrow;
    private final IDrawable arrow2;

    public LetterRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(164, 64);
        row = guiHelper.createDrawable(LibResource.row, 0, 0, 162, 18);
        arrow = guiHelper.createDrawable(LibResource.row, 162, 0, 17, 30);
        arrow2 = guiHelper.createDrawable(LibResource.row, 179, 0, 21, 16);
    }

    @Nonnull
    @Override
    public String getUid() { return "bb.letter"; }

    @Nonnull
    @Override
    public String getTitle() { return StatCollector.translateToLocal("bb.jei.letter"); }

    @Nonnull
    @Override
    public IDrawable getBackground() { return background; }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        row.draw(minecraft);
        arrow.draw(minecraft, 44, 19);
        arrow2.draw(minecraft, 64, 41);
        minecraft.getRenderItem().renderItemIntoGUI(new ItemStack(BBItems.letter), 45, 43);
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {}

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        if(!(recipeWrapper instanceof LetterRecipeWrapper))
            return;
        LetterRecipeWrapper wrapper = (LetterRecipeWrapper) recipeWrapper;
        int index = 0, posX = 0;
        for(Object o : wrapper.getInputs()) {
            recipeLayout.getItemStacks().init(index, true, posX, 0);
            if(o instanceof Collection)
                recipeLayout.getItemStacks().set(index, ((Collection<ItemStack>) o));
            else
                recipeLayout.getItemStacks().set(index, ((ItemStack) o));
            index++;
            posX += 18;
        }
        recipeLayout.getItemStacks().init(index, false, 86, 42);
        recipeLayout.getItemStacks().set(index, (ItemStack) wrapper.getOutputs().get(0));
    }
}
