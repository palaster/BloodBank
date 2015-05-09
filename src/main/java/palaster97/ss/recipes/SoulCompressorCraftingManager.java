package palaster97.ss.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import palaster97.ss.blocks.SSBlocks;
import palaster97.ss.core.helpers.SSItemStackHelper;
import palaster97.ss.items.SSItems;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SoulCompressorCraftingManager {

	private static final SoulCompressorCraftingManager instance = new SoulCompressorCraftingManager();
    private final List<IRecipe> recipes = Lists.newArrayList();

    public static SoulCompressorCraftingManager getInstance() { return instance; }

    private SoulCompressorCraftingManager() {
    	
    	// Blocks
    	func_180302_a(new ShapedOreRecipe(new ItemStack(SSBlocks.playerSoulManipulator), "xyx", "xzx", "xxx", 'x', "plankWood", 'z', SSItemStackHelper.getSoulItemStack(2), 'y', SSItems.soulBinder));
    	addRecipe(new ItemStack(SSBlocks.worldSoulManipulator), "xyx", "xzx", "xxx", 'x', Blocks.stone, 'z', SSItemStackHelper.getSoulItemStack(2), 'y', SSItems.worldSoulBinder);
    	addRecipe(new ItemStack(SSBlocks.conjuringTablet), "xyx", "yzy", "xyx", 'x', Blocks.end_stone, 'y', Blocks.nether_brick, 'z', SSItemStackHelper.getSoulItemStack(3));
    	
    	// Items
    	addRecipe(new ItemStack(SSItems.atmanSword), "x", "x", "y", 'x', SSItemStackHelper.getSoulItemStack(0), 'y', Items.stick);
    	addRecipe(new ItemStack(SSItems.soulBinder), "xyx", "yzy", "xyx", 'x', Items.gold_nugget, 'y', Blocks.redstone_block, 'z', Items.ender_pearl);
    	addRecipe(new ItemStack(SSItems.worldSoulBinder), "xyx", "yzy", "xyx", 'x', Items.gold_nugget, 'y', Blocks.quartz_block, 'z', Items.ender_pearl);
    	addRecipe(new ItemStack(SSItems.magicBow), " xy", "z y", " xy", 'z', SSItemStackHelper.getSoulItemStack(1), 'x', Items.stick, 'y', Items.string);
    	addRecipe(new ItemStack(SSItems.magicBow), "yx ", "y z", "yx ", 'z', SSItemStackHelper.getSoulItemStack(1), 'x', Items.stick, 'y', Items.string);
    	
        Collections.sort(recipes, new Comparator<Object>() {
        	public int compare(IRecipe p_compare_1_, IRecipe p_compare_2_) { return p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes ? 1 : (p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes ? -1 : (p_compare_2_.getRecipeSize() < p_compare_1_.getRecipeSize() ? -1 : (p_compare_2_.getRecipeSize() > p_compare_1_.getRecipeSize() ? 1 : 0))); }
            public int compare(Object p_compare_1_, Object p_compare_2_) { return compare((IRecipe)p_compare_1_, (IRecipe)p_compare_2_); }
        });
    }

    public ShapedRecipes addRecipe(ItemStack p_92103_1_, Object ... p_92103_2_) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if(p_92103_2_[i] instanceof String[]) {
            String[] astring = (String[])((String[])p_92103_2_[i++]);
            for(int l = 0; l < astring.length; ++l) {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while(p_92103_2_[i] instanceof String) {
                String s2 = (String)p_92103_2_[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }
        HashMap<Character, ItemStack> hashmap;
        for(hashmap = Maps.newHashMap(); i < p_92103_2_.length; i += 2) {
            Character character = (Character)p_92103_2_[i];
            ItemStack itemstack1 = null;
            if (p_92103_2_[i + 1] instanceof Item)
                itemstack1 = new ItemStack((Item)p_92103_2_[i + 1]);
            else if (p_92103_2_[i + 1] instanceof Block)
                itemstack1 = new ItemStack((Block)p_92103_2_[i + 1], 1, 32767);
            else if(p_92103_2_[i + 1] instanceof ItemStack)
                itemstack1 = (ItemStack)p_92103_2_[i + 1];
            hashmap.put(character, itemstack1);
        }
        ItemStack[] aitemstack = new ItemStack[j * k];
        for(int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);
            if(hashmap.containsKey(Character.valueOf(c0)))
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
            else
                aitemstack[i1] = null;
        }
        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, p_92103_1_);
        recipes.add(shapedrecipes);
        return shapedrecipes;
    }

    public void addShapelessRecipe(ItemStack p_77596_1_, Object ... p_77596_2_) {
        ArrayList<ItemStack> arraylist = Lists.newArrayList();
        Object[] aobject = p_77596_2_;
        int i = p_77596_2_.length;
        for(int j = 0; j < i; ++j) {
            Object object1 = aobject[j];
            if(object1 instanceof ItemStack) {
                arraylist.add(((ItemStack)object1).copy());
            } else if(object1 instanceof Item) {
                arraylist.add(new ItemStack((Item)object1));
            } else {
                if(!(object1 instanceof Block))
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object1.getClass().getName() + "!");
                arraylist.add(new ItemStack((Block)object1));
            }
        }
        recipes.add(new ShapelessRecipes(p_77596_1_, arraylist));
    }

    public void func_180302_a(IRecipe p_180302_1_) { recipes.add(p_180302_1_); }

    public ItemStack findMatchingRecipe(InventoryCrafting p_82787_1_, World worldIn) {
        Iterator<IRecipe> iterator = recipes.iterator();
        IRecipe irecipe;
        do {
            if(!iterator.hasNext())
                return null;
            irecipe = (IRecipe)iterator.next();
        }
        while (!irecipe.matches(p_82787_1_, worldIn));
        return irecipe.getCraftingResult(p_82787_1_);
    }

    public ItemStack[] func_180303_b(InventoryCrafting p_180303_1_, World worldIn) {
        Iterator<IRecipe> iterator = recipes.iterator();
        while(iterator.hasNext()) {
            IRecipe irecipe = (IRecipe)iterator.next();
            if(irecipe.matches(p_180303_1_, worldIn))
            	return irecipe.getRemainingItems(p_180303_1_);
        }
        ItemStack[] aitemstack = new ItemStack[p_180303_1_.getSizeInventory()];
        for(int i = 0; i < aitemstack.length; ++i)
            aitemstack[i] = p_180303_1_.getStackInSlot(i);
        return aitemstack;
    }
    
    public List<IRecipe> getRecipeList() { return recipes; }
}
