package palaster.bb.recipes;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import palaster.bb.api.recipes.ShapedBloodRecipes;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.core.helpers.NBTHelper;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.careers.CareerCleric;
import palaster.bb.entities.careers.CareerMonsterTamer;
import palaster.bb.entities.careers.CareerWorkshopWitch;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemCareerPamphlet;
import palaster.bb.items.ItemToken;

public class BBRecipes {

	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}

	private static void registerSmeltingRecipes() {}

	private static void registerCraftingRecipes() {
		// Blocks
		GameRegistry.addRecipe(new ItemStack(BBBlocks.voidAnchor), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.OBSIDIAN), 'y', new ItemStack(Items.ENDER_PEARL), 'z', new ItemStack(Blocks.ENDER_CHEST));
		GameRegistry.addRecipe(new ItemStack(BBBlocks.communityTool), "xyx", "yzy", "xyx", 'x', new ItemStack(Items.GOLD_INGOT), 'y', new ItemStack(Blocks.QUARTZ_BLOCK), 'z', new ItemStack(Blocks.CHEST));
		GameRegistry.addRecipe(new ItemStack(BBBlocks.tntAbsorber), "xxx", "yyy", " z ", 'x', Items.WATER_BUCKET, 'y', Blocks.TNT, 'z', Items.ENDER_PEARL);
		GameRegistry.addRecipe(new ItemStack(BBBlocks.bloodTicker), "xyx", "yzy", "xyx", 'x', Items.REPEATER, 'y', Items.ENDER_PEARL, 'z', Items.CLOCK);
		GameRegistry.addRecipe(new ItemStack(BBBlocks.desalinator), "xyx", "xzx", "xyx", 'x', Blocks.COBBLESTONE, 'y', Items.BOWL, 'z', Items.FLINT_AND_STEEL);

		// Items
		GameRegistry.addRecipe(new ItemStack(BBItems.resurrectionStone), "xyx", "yzy", "xyx", 'x', Blocks.SOUL_SAND, 'y', Items.GLOWSTONE_DUST, 'z', BBItems.soulGem);
		GameRegistry.addRecipe(new ItemStack(BBItems.ghostWhisper), "xzx", "zyz", "xzx", 'x', Blocks.SOUL_SAND, 'y', BBItems.soulGem, 'z', Blocks.GLASS);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BBItems.clericStaff), " xy", " zx", "z  ", 'y', Items.IRON_INGOT, 'x', "dustSalt", 'z', Items.STICK));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.rpgIntro), new ItemStack(Items.BOOK), new ItemStack(Items.CLOCK), new ItemStack(Items.MAP));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BBItems.purifyingBook), new ItemStack(Items.BOOK), "dustSalt"));
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, ""), new ItemStack(Items.WRITABLE_BOOK), new ItemStack(BBItems.pinkSlip));
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, new CareerBloodSorcerer().getClass().getName()), new ItemStack(BBItems.careerPamphlet), Items.GLASS_BOTTLE, Items.ROTTEN_FLESH);
		GameRegistry.addRecipe(new ShapelessOreRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, new CareerCleric().getClass().getName()), new ItemStack(BBItems.careerPamphlet), BBItems.clericStaff, "dustSalt"));
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, new CareerMonsterTamer().getClass().getName()), new ItemStack(BBItems.careerPamphlet), BBItems.whip, Items.EGG);
		GameRegistry.addShapelessRecipe(NBTHelper.setStringToItemStack(new ItemStack(BBItems.careerPamphlet), ItemCareerPamphlet.TAG_STRING_CAREER_CLASS, new CareerWorkshopWitch().getClass().getName()), new ItemStack(BBItems.careerPamphlet), Items.BREWING_STAND, Items.CAULDRON);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BBItems.vampireSigil), "xyx", "yzy", "xyx", 'x', Blocks.STONE, 'y', "dyeRed", 'z', Items.ROTTEN_FLESH));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BBItems.pinkSlip), Items.PAPER, "dyePink", Items.FLINT_AND_STEEL));
		
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.boundPlayer), new ItemStack(Blocks.STONE_SLAB), new ItemStack(Items.BONE));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.boundBloodBottle), new ItemStack(BBItems.boundPlayer), new ItemStack(BBItems.bloodBottle));

		GameRegistry.addRecipe(new ItemStack(BBItems.armorActivator), "xax", "bxc", "xdx", 'x', Items.GLOWSTONE_DUST, 'a', Items.DIAMOND_HELMET, 'b', Items.DIAMOND_CHESTPLATE, 'c', Items.DIAMOND_LEGGINGS, 'd', Items.DIAMOND_BOOTS);
		
		GameRegistry.addRecipe(NBTHelper.setIntegerToItemStack(new ItemStack(BBItems.token, 1, 1), ItemToken.TAG_INT_TOKEN, 0), "xyx", "yzy", "xyx", 'x', Blocks.STONE, 'y', Items.GOLD_NUGGET, 'z', BBItems.token);
		GameRegistry.addRecipe(NBTHelper.setIntegerToItemStack(new ItemStack(BBItems.token, 1, 2), ItemToken.TAG_INT_TOKEN, 0), "xya", "yzy", "ayx", 'a', BBItems.vampireSigil, 'x', Items.DIAMOND_SWORD, 'y', Items.ROTTEN_FLESH, 'z', BBItems.token);
		GameRegistry.addRecipe(new ShapedOreRecipe(NBTHelper.setIntegerToItemStack(new ItemStack(BBItems.token, 1, 2), ItemToken.TAG_INT_TOKEN, 1), "yxw", " z ", "wxy", 'w', Items.REDSTONE, 'x', Blocks.NOTEBLOCK, 'y', "record", 'z', BBItems.token));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BBItems.talisman, 1, 0), new ItemStack(Items.PAPER), "dyeBlack", new ItemStack(Items.GLOWSTONE_DUST)));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.talisman, 1, 1), new ItemStack(BBItems.talisman, 1, 0), new ItemStack(Items.SUGAR));
		GameRegistry.addShapelessRecipe(new ItemStack(BBItems.talisman, 1, 2), new ItemStack(BBItems.talisman, 1, 0), new ItemStack(Items.SPIDER_EYE));
		
		RecipeSorter.register("bb:shapedblood", ShapedBloodRecipes.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
		
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffSkeleton), "  x", " y ", "y  ", 'x', new ItemStack(Items.SKULL, 1, 0), 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffEfreet), "  x", " y ", "y  ", 'x', Items.MAGMA_CREAM, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffTime), "  x", " y ", "y  ", 'x', Items.CLOCK, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffVoidWalker), "  x", " y ", "y  ", 'x', Blocks.END_STONE, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffHungryShadows), "  x", " y ", "y  ", 'x', Items.NETHER_STAR, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.animalHerder), "  x", " y ", "y  ", 'x', Items.WHEAT, 'y', Items.STICK);
		addShapedBloodRecipe(500, new ItemStack(BBItems.bloodBook), "yyy", "yxy", "yyy", 'x', BBItems.vampireSigil, 'y', Items.PAPER);
		
		ItemStack health = new ItemStack(Items.POTIONITEM);
		PotionUtils.addPotionToItemStack(health, PotionType.getPotionTypeForName("strong_healing"));
		ItemStack damage = new ItemStack(Items.POTIONITEM);
		PotionUtils.addPotionToItemStack(damage, PotionType.getPotionTypeForName("strong_harming"));
		addShapedBloodRecipe(500, new ItemStack(BBItems.wormEater), "wyx", "yzy", "xyw", 'w', health, 'x', damage, 'y', Blocks.OBSIDIAN, 'z', new ItemStack(Items.SKULL, 1, 1));
	}
	
	public static ShapedBloodRecipes addShapedBloodRecipe(int bloodCost, ItemStack stack, Object... recipeComponents) {
		String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if(recipeComponents[i] instanceof String[]) {
            String[] astring = (String[])((String[])recipeComponents[i++]);
            for(int l = 0; l < astring.length; ++l) {
                String s2 = astring[l];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        } else
            while(recipeComponents[i] instanceof String) {
                String s1 = (String)recipeComponents[i++];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        Map<Character, ItemStack> map;
        for(map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2) {
            Character character = (Character)recipeComponents[i];
            ItemStack itemstack = null;
            if(recipeComponents[i + 1] instanceof Item)
                itemstack = new ItemStack((Item)recipeComponents[i + 1]);
            else if(recipeComponents[i + 1] instanceof Block)
                itemstack = new ItemStack((Block)recipeComponents[i + 1], 1, 32767);
            else if(recipeComponents[i + 1] instanceof ItemStack)
                itemstack = (ItemStack)recipeComponents[i + 1];
            map.put(character, itemstack);
        }
        ItemStack[] aitemstack = new ItemStack[j * k];
        for(int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);
            if(map.containsKey(Character.valueOf(c0)))
                aitemstack[i1] = ((ItemStack)map.get(Character.valueOf(c0))).copy();
            else
            	aitemstack[i1] = null;
        }
        ShapedBloodRecipes shapedrecipes = new ShapedBloodRecipes(bloodCost, j, k, aitemstack, stack);
        CraftingManager.getInstance().addRecipe(shapedrecipes);
        return shapedrecipes;
	}
}
