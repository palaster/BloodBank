package palaster.bb.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import palaster.bb.api.BBApi;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.BlockBonfire;
import palaster.bb.items.BBItems;

public class BBRecipes {

	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
		registerLetterRecipes();
	}

	private static void registerSmeltingRecipes() {}

	private static void registerCraftingRecipes() {
		//Blocks
		GameRegistry.addRecipe(new ItemStack(BBBlocks.voidAnchor), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.obsidian), 'y', new ItemStack(Items.ender_pearl), 'z', new ItemStack(Blocks.ender_chest));
		GameRegistry.addRecipe(new ItemStack(BBBlocks.communityTool), "xyx", "yzy", "xyx", 'x', new ItemStack(Items.emerald), 'y', new ItemStack(Blocks.quartz_block), 'z', new ItemStack(Blocks.chest));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BBBlocks.bonfire), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.torch), 'y', "logWood", 'z', new ItemStack(Items.fire_charge)));

		// Items
		GameRegistry.addRecipe(new ItemStack(BBItems.bbResources, 1, 0), "xxx", "xyx", "xxx", 'x', Items.paper, 'y', Blocks.netherrack);
		GameRegistry.addRecipe(new ItemStack(BBItems.letter), "xxx", "xyx", "xxx", 'x', Items.paper, 'y', Items.feather);
		GameRegistry.addRecipe(new ItemStack(BBItems.bbResources, 1, 3), "xyx", "yzy", "xyx", 'x', Blocks.stone, 'y', Items.emerald, 'z', Items.rotten_flesh);
		GameRegistry.addRecipe(new ItemStack(BBItems.bbResources, 1, 4), "   ", "yzy", " y ", 'y', Items.netherbrick, 'z', Items.fire_charge);

		GameRegistry.addRecipe(new VampireSigilRecipe());
		RecipeSorter.register("bb:vampireSigil", VampireSigilRecipe.class, RecipeSorter.Category.SHAPELESS, "");
		GameRegistry.addRecipe(new FlamesSpellRecipe());
		RecipeSorter.register("bb:flames", FlamesSpellRecipe.class, RecipeSorter.Category.SHAPELESS, "");
	}

	private static void registerLetterRecipes() {
		// Blocks


		// Items
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffSkeleton), new ItemStack(Items.stick, 2), new ItemStack(Items.bone));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffEfreet), new ItemStack(Items.stick, 2), new ItemStack(Items.magma_cream));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffTime), new ItemStack(Items.stick, 2), new ItemStack(Items.clock));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffVoidWalker), new ItemStack(Items.stick, 2), new ItemStack(Blocks.end_stone));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffHungryShadows), new ItemStack(Items.stick, 2), new ItemStack(Items.nether_star));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.animalHerder), new ItemStack(Items.stick, 2), new ItemStack(Items.wheat));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.bloodBook), new ItemStack(Items.paper, 8), new ItemStack(BBItems.bbResources, 1, 3));

		ItemStack health = new ItemStack(Items.potionitem, 2);
		PotionUtils.addPotionToItemStack(health, PotionType.getPotionTypeForName("strong_healing"));
		ItemStack damage = new ItemStack(Items.potionitem, 2);
		PotionUtils.addPotionToItemStack(damage, PotionType.getPotionTypeForName("strong_harming"));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.bbResources, 1, 2), health, damage, new ItemStack(Items.skull, 1, 1), new ItemStack(Blocks.obsidian, 4));
	}
}
