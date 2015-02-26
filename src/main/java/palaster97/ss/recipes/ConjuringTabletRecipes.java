package palaster97.ss.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import palaster97.ss.core.helpers.SSItemStackHelper;
import palaster97.ss.items.SSItems;

public class ConjuringTabletRecipes {

	private final static List<ConjuringTabletRecipe> ct = new ArrayList<ConjuringTabletRecipe>();
	
	public static void addRecipe(ConjuringTabletRecipe ctr) { ct.add(ctr); }
	
	public static boolean checkRecipe(ItemStack major, ItemStack[] input) {
		for(int i = 0; i < ct.toArray().length; i++) {
			if(ct.get(i).checkMajor(major))
				if(ct.get(i).matches(input))
					return true;
		}
		return false;
	}
	
	public static ConjuringTabletRecipe getRecipe(ItemStack major, ItemStack[] input) {
		for(int i = 0; i < ct.toArray().length; i++) {
			if(ct.get(i).checkMajor(major))
				if(ct.get(i).matches(input))
					return ct.get(i);
		}
		return null;
	}
	
	public static void registerConjuringTablet() {
		addRecipe(new ConjuringTabletRecipe(SSItemStackHelper.getSoulItemStack(2), 54, new ItemStack(Items.rotten_flesh)));
		addRecipe(new ConjuringTabletRecipe(SSItemStackHelper.getSoulItemStack(1), "shoeElf", new ItemStack(Items.book), new ItemStack(Blocks.chest), new ItemStack(Items.leather)));
	}
}
