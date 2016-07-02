package palaster.bb.compat.jei.blood;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class BloodRecipeCategory extends BlankRecipeCategory<BloodRecipeWrapper> {
	
	public static final String categoryUID = "bb.blood";

	private static final int craftOutputSlot = 0;
	private static final int craftInputSlot1 = 1;

	public static final int width = 116;
	public static final int height = 54;

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final ICraftingGridHelper craftingGridHelper;
	
	public BloodRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
		background = guiHelper.createDrawable(location, 29, 16, width, height);
		craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }
	
	@Override
	public String getUid() { return categoryUID; }

	@Override
	public String getTitle() { return I18n.format("bb.jei.blood"); }

	@Override
	public IDrawable getBackground() { return background; }

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BloodRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(craftOutputSlot, false, 94, 18);
		for(int y = 0; y < 3; ++y)
			for(int x = 0; x < 3; ++x) {
				int index = craftInputSlot1 + x + (y * 3);
				guiItemStacks.init(index, true, x * 18, y * 18);
			}
		if(recipeWrapper instanceof BloodRecipeWrapper) {
			BloodRecipeWrapper wrapper = (BloodRecipeWrapper) recipeWrapper;
			craftingGridHelper.setInput(guiItemStacks, wrapper.getInputs(), wrapper.getWidth(), wrapper.getHeight());
			craftingGridHelper.setOutput(guiItemStacks, wrapper.getOutputs());
		} else {
			craftingGridHelper.setInput(guiItemStacks, recipeWrapper.getInputs());
			craftingGridHelper.setOutput(guiItemStacks, recipeWrapper.getOutputs());
		}
	}	
}
