package palaster97.ss.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import palaster97.ss.blocks.SSBlocks;
import palaster97.ss.core.helpers.SSItemStackHelper;
import palaster97.ss.items.SSItems;

public class SSRecipes {
	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}

	private static void registerSmeltingRecipes() { GameRegistry.addSmelting(Blocks.soul_sand, SSItemStackHelper.getSoulItemStack(0), 0); }

	private static void registerCraftingRecipes() { GameRegistry.addRecipe(new ShapedOreRecipe(SSBlocks.soulCompressor, "zzz", "xyx", 'y', SSItems.mobSouls, 'x', "slabWood", 'z', new ItemStack(Blocks.stone_slab, 1, 0))); }
}
