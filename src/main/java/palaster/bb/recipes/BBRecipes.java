package palaster.bb.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
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

	private static void registerCraftingRecipes() {
		//Blocks
		GameRegistry.addRecipe(new ItemStack(BBBlocks.voidAnchor), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.OBSIDIAN), 'y', new ItemStack(Items.ENDER_PEARL), 'z', new ItemStack(Blocks.ENDER_CHEST));
		GameRegistry.addRecipe(new ItemStack(BBBlocks.communityTool), "xyx", "yzy", "xyx", 'x', new ItemStack(Items.EMERALD), 'y', new ItemStack(Blocks.QUARTZ_BLOCK), 'z', new ItemStack(Blocks.CHEST));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BBBlocks.bonfire), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.TORCH), 'y', "logWood", 'z', new ItemStack(Items.FIRE_CHARGE)));
		GameRegistry.addRecipe(new ItemStack(BBBlocks.tntAbsorber), "xxx", "yyy", " z ", 'x', Items.WATER_BUCKET, 'y', Blocks.TNT, 'z', Items.ENDER_PEARL);

		// Items
		GameRegistry.addRecipe(new ItemStack(BBItems.letter), "xxx", "xyx", "xxx", 'x', Items.PAPER, 'y', Items.FEATHER);
		GameRegistry.addRecipe(new ItemStack(BBItems.resurrectionStone), "xyx", "yzy", "xyx", 'x', Blocks.SOUL_SAND, 'y', Items.EMERALD, 'z', Items.NETHER_STAR);
		GameRegistry.addRecipe(new ItemStack(BBItems.ghostWhisper), "xyx", "yzy", "xyx", 'x', Blocks.SOUL_SAND, 'y', Items.DIAMOND, 'z', Blocks.GLASS);
		
		GameRegistry.addRecipe(new ItemStack(BBItems.bbResources, 1, 0), "xxx", "xyx", "xxx", 'x', Items.PAPER, 'y', Blocks.NETHERRACK);
		GameRegistry.addRecipe(new ItemStack(BBItems.bbResources, 1, 3), "xyx", "yzy", "xyx", 'x', Blocks.STONE, 'y', Items.EMERALD, 'z', Items.ROTTEN_FLESH);
		GameRegistry.addRecipe(new ItemStack(BBItems.bbResources, 1, 4), "   ", "yzy", " y ", 'y', Items.NETHERBRICK, 'z', Items.FIRE_CHARGE);
		
		GameRegistry.addRecipe(new ItemStack(BBItems.sandHelmet), "xxx", "x x", 'x', Items.STRING);
		GameRegistry.addRecipe(new ItemStack(BBItems.sandChestplate), "x x", "xxx", "xxx", 'x', Items.STRING);
		GameRegistry.addRecipe(new ItemStack(BBItems.sandLeggings), "xxx", "x x", "x x", 'x', Items.STRING);
		GameRegistry.addRecipe(new ItemStack(BBItems.sandBoots), "x x", "x x", 'x', Items.STRING);
		GameRegistry.addRecipe(new ItemStack(BBItems.armorActivator), "xax", "bxc", "xdx", 'x', Items.GLOWSTONE_DUST, 'a', Items.DIAMOND_HELMET, 'b', Items.DIAMOND_CHESTPLATE, 'c', Items.DIAMOND_LEGGINGS, 'd', Items.DIAMOND_BOOTS);
	}

	private static void registerLetterRecipes() {
		// Blocks

		// Items
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffSkeleton), new ItemStack(Items.STICK, 2), new ItemStack(Items.BONE));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffEfreet), new ItemStack(Items.STICK, 2), new ItemStack(Items.MAGMA_CREAM));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffTime), new ItemStack(Items.STICK, 2), new ItemStack(Items.CLOCK));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffVoidWalker), new ItemStack(Items.STICK, 2), new ItemStack(Blocks.END_STONE));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.staffHungryShadows), new ItemStack(Items.STICK, 2), new ItemStack(Items.NETHER_STAR));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.animalHerder), new ItemStack(Items.STICK, 2), new ItemStack(Items.WHEAT));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.bloodBook), new ItemStack(Items.PAPER, 8), new ItemStack(BBItems.bbResources, 1, 3));

		ItemStack health = new ItemStack(Items.POTIONITEM, 2);
		PotionUtils.addPotionToItemStack(health, PotionType.getPotionTypeForName("strong_healing"));
		ItemStack damage = new ItemStack(Items.POTIONITEM, 2);
		PotionUtils.addPotionToItemStack(damage, PotionType.getPotionTypeForName("strong_harming"));
		BBApi.registerLetterRecipe(new ItemStack(BBItems.bbResources, 1, 2), health, damage, new ItemStack(Items.SKULL, 1, 1), new ItemStack(Blocks.OBSIDIAN, 4));
	}
}
