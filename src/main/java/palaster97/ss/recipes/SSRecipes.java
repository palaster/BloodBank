package palaster97.ss.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import palaster97.ss.blocks.SSBlocks;

public class SSRecipes {
	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}

	private static void registerSmeltingRecipes() {}

	private static void registerCraftingRecipes() { GameRegistry.addRecipe(new ShapedOreRecipe(SSBlocks.soulCompressor, "zzz", "xyx", 'y', Items.ender_pearl, 'x', "slabWood", 'z', new ItemStack(Blocks.stone_slab, 1, 0))); }
}
