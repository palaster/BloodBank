package palaster.bb.compat.jei.blood;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import palaster.bb.api.recipes.ShapedBloodRecipes;

public class BloodRecipeWrapper implements IRecipeWrapper {
	
	private final ShapedBloodRecipes blood;

    public BloodRecipeWrapper(ShapedBloodRecipes recipeLetter) { this.blood = recipeLetter; }

	@Override
	public List<ItemStack> getInputs() { return Arrays.asList(blood.recipeItems); }

	@Override
	public List<ItemStack> getOutputs() { return Collections.singletonList(blood.getRecipeOutput()); }

	@Override
	public List<FluidStack> getFluidInputs() { return null; }

	@Override
	public List<FluidStack> getFluidOutputs() { return null; }

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) { minecraft.fontRendererObj.drawString(I18n.format("bb.jei.bloodCost") + " : " + blood.recipeBloodCost, 60, 0, 0x8A0707); }

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) { return null; }

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) { return false; }
	
	
	public int getWidth() { return blood.recipeWidth; }
	
	public int getHeight() { return blood.recipeHeight; }
}
