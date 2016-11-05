package palaster.bb.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.careers.CareerCleric;
import palaster.bb.entities.careers.CareerMonsterTamer;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemCareerPamphlet;
import palaster.bb.items.ItemToken;
import palaster.libpal.core.helpers.NBTHelper;

public class BBRecipes {

	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}

	private static void registerSmeltingRecipes() {}

	private static void registerCraftingRecipes() {
		// Blocks
		GameRegistry.addShapedRecipe(new ItemStack(BBBlocks.voidAnchor), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.OBSIDIAN), 'y', new ItemStack(Items.ENDER_PEARL), 'z', new ItemStack(Blocks.ENDER_CHEST));
		GameRegistry.addShapedRecipe(new ItemStack(BBBlocks.tntAbsorber), "xxx", "yyy", " z ", 'x', Items.WATER_BUCKET, 'y', Blocks.TNT, 'z', Items.ENDER_PEARL);
		GameRegistry.addShapedRecipe(new ItemStack(BBBlocks.bloodTicker), "xyx", "yzy", "xyx", 'x', Items.REPEATER, 'y', Items.ENDER_PEARL, 'z', Items.CLOCK);
		GameRegistry.addShapedRecipe(new ItemStack(BBBlocks.desalinator), "xyx", "xzx", "xyx", 'x', Blocks.COBBLESTONE, 'y', Items.BOWL, 'z', Items.FLINT_AND_STEEL);

		// Items
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.staffSkeleton), "  x", " y ", "y  ", 'x', new ItemStack(Items.SKULL, 1, 0), 'y', Items.STICK);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.staffEfreet), "  x", " y ", "y  ", 'x', Items.MAGMA_CREAM, 'y', Items.STICK);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.staffTime), "  x", " y ", "y  ", 'x', Items.CLOCK, 'y', Items.STICK);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.staffVoidWalker), "  x", " y ", "y  ", 'x', Blocks.END_STONE, 'y', Items.STICK);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.staffHungryShadows), "  x", " y ", "y  ", 'x', Items.NETHER_STAR, 'y', Items.STICK);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.animalHerder), "  x", " y ", "y  ", 'x', Items.WHEAT, 'y', Items.STICK);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.bloodBook), "yyy", "yxy", "yyy", 'x', BBItems.vampireSigil, 'y', Items.PAPER);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.resurrectionStone), "xyx", "yzy", "xyx", 'x', Blocks.SOUL_SAND, 'y', Items.GLOWSTONE_DUST, 'z', BBItems.soulGem);
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.ghostWhisper), "xzx", "zyz", "xzx", 'x', Blocks.SOUL_SAND, 'y', BBItems.soulGem, 'z', Blocks.GLASS);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BBItems.clericStaff), " xy", " zx", "z  ", 'y', Items.IRON_INGOT, 'x', "dustSalt", 'z', Items.STICK));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.rpgIntro), new ItemStack(Items.BOOK), new ItemStack(Items.CLOCK), new ItemStack(Items.MAP));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BBItems.purifyingBook), new ItemStack(Items.BOOK), "dustSalt"));
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, ""), new ItemStack(Items.WRITABLE_BOOK), new ItemStack(BBItems.pinkSlip));
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, new CareerBloodSorcerer().getClass().getName()), new ItemStack(BBItems.careerPamphlet), Items.GLASS_BOTTLE, Items.ROTTEN_FLESH);
		GameRegistry.addRecipe(new ShapelessOreRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, new CareerCleric().getClass().getName()), new ItemStack(BBItems.careerPamphlet), BBItems.clericStaff, "dustSalt"));
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, new CareerMonsterTamer().getClass().getName()), new ItemStack(BBItems.careerPamphlet), BBItems.whip, Items.EGG);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BBItems.vampireSigil), "xyx", "yzy", "xyx", 'x', Blocks.STONE, 'y', "dyeRed", 'z', Items.ROTTEN_FLESH));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BBItems.pinkSlip), Items.PAPER, "dyePink", Items.FLINT_AND_STEEL));
		
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.boundPlayer), new ItemStack(Blocks.STONE_SLAB), new ItemStack(Items.BONE));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.boundBloodBottle), new ItemStack(BBItems.boundPlayer), new ItemStack(BBItems.bloodBottle));

		GameRegistry.addShapedRecipe(new ItemStack(BBItems.armorActivator), "xax", "bxc", "xdx", 'x', Items.GLOWSTONE_DUST, 'a', Items.DIAMOND_HELMET, 'b', Items.DIAMOND_CHESTPLATE, 'c', Items.DIAMOND_LEGGINGS, 'd', Items.DIAMOND_BOOTS);
		
		GameRegistry.addShapedRecipe(NBTHelper.setIntegerToItemStack(new ItemStack(BBItems.token, 1, 1), ItemToken.TAG_INT_TOKEN, 0), "xyx", "yzy", "xyx", 'x', Blocks.STONE, 'y', Items.GOLD_NUGGET, 'z', BBItems.token);
		GameRegistry.addShapedRecipe(NBTHelper.setIntegerToItemStack(new ItemStack(BBItems.token, 1, 2), ItemToken.TAG_INT_TOKEN, 0), "xya", "yzy", "ayx", 'a', BBItems.vampireSigil, 'x', Items.DIAMOND_SWORD, 'y', Items.ROTTEN_FLESH, 'z', BBItems.token);
		GameRegistry.addRecipe(new ShapedOreRecipe(NBTHelper.setIntegerToItemStack(new ItemStack(BBItems.token, 1, 2), ItemToken.TAG_INT_TOKEN, 1), "yxw", " z ", "wxy", 'w', Items.REDSTONE, 'x', Blocks.NOTEBLOCK, 'y', "record", 'z', BBItems.token));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BBItems.talisman, 1, 0), new ItemStack(Items.PAPER), "dyeBlack", new ItemStack(Items.GLOWSTONE_DUST)));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.talisman, 1, 1), new ItemStack(BBItems.talisman, 1, 0), new ItemStack(Items.SUGAR));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.talisman, 1, 2), new ItemStack(BBItems.talisman, 1, 0), new ItemStack(Items.SPIDER_EYE));
		
		ItemStack health = new ItemStack(Items.POTIONITEM);
		PotionUtils.addPotionToItemStack(health, PotionType.getPotionTypeForName("strong_healing"));
		ItemStack damage = new ItemStack(Items.POTIONITEM);
		PotionUtils.addPotionToItemStack(damage, PotionType.getPotionTypeForName("strong_harming"));
		GameRegistry.addShapedRecipe(new ItemStack(BBItems.wormEater), "wyx", "yzy", "xyw", 'w', health, 'x', damage, 'y', Blocks.OBSIDIAN, 'z', new ItemStack(Items.SKULL, 1, 1));
	}
}
