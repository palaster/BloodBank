package palaster.bb.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import palaster.bb.blocks.BBBlocks;

public class BBRecipes {
	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}

	private static void registerSmeltingRecipes() {}

	private static void registerCraftingRecipes() { GameRegistry.addRecipe(new ShapedOreRecipe(BBBlocks.soulCompressor, "zzz", "xyx", 'y', Items.ender_pearl, 'x', "slabWood", 'z', new ItemStack(Blocks.stone_slab, 1, 0))); }
}
