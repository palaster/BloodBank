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
import palaster.bb.api.recipes.ShapedBloodRecipes;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.items.BBItems;
import palaster.bb.libs.LibNBT;

public class BBRecipes {

	public static void init() {
		registerCraftingRecipes();
		registerSmeltingRecipes();
	}

	private static void registerSmeltingRecipes() {}

	private static void registerCraftingRecipes() {
		//Blocks
		GameRegistry.addRecipe(new ItemStack(BBBlocks.voidAnchor), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.OBSIDIAN), 'y', new ItemStack(Items.ENDER_PEARL), 'z', new ItemStack(Blocks.ENDER_CHEST));
		GameRegistry.addRecipe(new ItemStack(BBBlocks.communityTool), "xyx", "yzy", "xyx", 'x', new ItemStack(Items.EMERALD), 'y', new ItemStack(Blocks.QUARTZ_BLOCK), 'z', new ItemStack(Blocks.CHEST));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BBBlocks.bonfire), "xyx", "yzy", "xyx", 'x', new ItemStack(Blocks.TORCH), 'y', "logWood", 'z', new ItemStack(Items.FIRE_CHARGE)));
		GameRegistry.addRecipe(new ItemStack(BBBlocks.tntAbsorber), "xxx", "yyy", " z ", 'x', Items.WATER_BUCKET, 'y', Blocks.TNT, 'z', Items.ENDER_PEARL);

		// Items
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
		
		ItemStack tokenBoss = new ItemStack(BBItems.token);
		BBItemStackHelper.addNumberTagToItemStack(tokenBoss, LibNBT.number, 0);
		GameRegistry.addRecipe(tokenBoss, "xyx", "yzy", "xyx", 'x', Blocks.STONE, 'y', Items.GOLD_NUGGET, 'z', BBItems.token);
		
		RecipeSorter.register("bb:shapedblood", ShapedBloodRecipes.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
		
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffSkeleton), "  x", " y ", "y  ", 'x', Items.BONE, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffEfreet), "  x", " y ", "y  ", 'x', Items.MAGMA_CREAM, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffTime), "  x", " y ", "y  ", 'x', Items.CLOCK, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffVoidWalker), "  x", " y ", "y  ", 'x', Blocks.END_STONE, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.staffHungryShadows), "  x", " y ", "y  ", 'x', Items.NETHER_STAR, 'y', Items.STICK);
		addShapedBloodRecipe(250, new ItemStack(BBItems.animalHerder), "  x", " y ", "y  ", 'x', Items.WHEAT, 'y', Items.STICK);
		addShapedBloodRecipe(500, new ItemStack(BBItems.bloodBook), "yyy", "yxy", "yyy", 'x', new ItemStack(BBItems.bbResources, 1, 3), 'y', Items.PAPER);
		
		ItemStack health = new ItemStack(Items.POTIONITEM);
		PotionUtils.addPotionToItemStack(health, PotionType.getPotionTypeForName("strong_healing"));
		ItemStack damage = new ItemStack(Items.POTIONITEM);
		PotionUtils.addPotionToItemStack(damage, PotionType.getPotionTypeForName("strong_harming"));
		addShapedBloodRecipe(500, new ItemStack(BBItems.bbResources, 1, 2), "wyx", "yzy", "xyw", 'w', health, 'x', damage, 'y', Blocks.OBSIDIAN, 'z', new ItemStack(Items.SKULL, 1, 1));
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
