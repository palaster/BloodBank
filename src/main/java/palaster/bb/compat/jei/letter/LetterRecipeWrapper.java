package palaster.bb.compat.jei.letter;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import palaster.bb.api.recipes.RecipeLetter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class LetterRecipeWrapper implements IRecipeWrapper {

    private final RecipeLetter letter;

    public LetterRecipeWrapper(RecipeLetter recipeLetter) {
        this.letter = recipeLetter;
    }

    @Override
    public List getInputs() {
        if(letter != null && letter.getInput() != null) {
            List<Object> inputs = Arrays.asList(letter.getInput());
            return inputs;
        } else
            return null;
    }

    @Override
    public List getOutputs() {
        if(letter != null && letter.getOutput() != null) {
            List<ItemStack> outputs = Arrays.asList(letter.getOutput());
            return outputs;
        } else
            return null;
    }

    @Override
    public List<FluidStack> getFluidInputs() { return null; }

    @Override
    public List<FluidStack> getFluidOutputs() { return null; }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {}

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) { return null; }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) { return false; }
}
