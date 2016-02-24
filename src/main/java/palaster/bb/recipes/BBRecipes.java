package palaster.bb.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import palaster.bb.api.BBApi;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.items.BBItems;

public class BBRecipes {
	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
		registerLetterRecipes();
	}

	private static void registerSmeltingRecipes() {}

	private static void registerCraftingRecipes() {}

	private static void registerLetterRecipes() {
		// Blocks
		BBApi.registerLetterRecipe(new ItemStack(BBBlocks.worldManipulator), new ItemStack(Blocks.stone, 7), new ItemStack(Items.ender_pearl), new ItemStack(BBItems.worldBinder));
		BBApi.registerLetterRecipe(new ItemStack(BBBlocks.voidAnchor), new ItemStack(Blocks.obsidian, 4), new ItemStack(Items.ender_pearl, 4), new ItemStack(Blocks.ender_chest));

		// Items
		BBApi.registerLetterRecipe(new ItemStack(BBItems.playerBinder), new ItemStack(Items.gold_nugget, 4), new ItemStack(Items.ender_pearl), new ItemStack(Blocks.redstone_block, 4));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.worldBinder), new ItemStack(Items.gold_nugget, 4), new ItemStack(Items.ender_pearl), new ItemStack(Blocks.quartz_block, 4));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffTime), new ItemStack(Items.stick, 2), new ItemStack(Items.clock));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffSkeleton), new ItemStack(Items.stick, 2), new ItemStack(Items.bone));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.bloodBook), new ItemStack(Items.paper, 8), new ItemStack(BBItems.athame));
	}
}
