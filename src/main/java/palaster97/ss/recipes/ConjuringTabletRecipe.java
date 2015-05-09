package palaster97.ss.recipes;

import net.minecraft.item.ItemStack;
import palaster97.ss.items.ItemSoul;

public class ConjuringTabletRecipe {
	
	private final ItemStack major;
	private final int output;
	private final String name;
	private final ItemStack[] input;
	
	public ConjuringTabletRecipe(ItemStack major, int output, ItemStack... input) {
		this.major = major;
		this.output = output;
		this.name = null;
		this.input = input;
	}
	
	public ConjuringTabletRecipe(ItemStack major, String name, ItemStack... input) {
		this.major = major;
		this.output = -1;
		this.name = name;
		this.input = input;
	}
	
	public boolean checkMajor(ItemStack check) {
		if(major != null && check != null)
			if(major.getItem() == check.getItem())
				if(major.getItem() instanceof ItemSoul && check.getItem() instanceof ItemSoul) {
					if(major.hasTagCompound() && check.hasTagCompound())
						if(major.getTagCompound().getInteger("Level") == check.getTagCompound().getInteger("Level"))
							return true;
					else
						return false;
				}
				else
					return true;
		return false;
	}
	
	public boolean matches(ItemStack... check) {
		if(input.length <= check.length) {
			int temp = 0;
			for(int i = 0; i < input.length; i++)
				for(int j = 0; j < check.length; j++)
					if(input[i] != null && check[j] != null)
						if(input[i].getItem() == check[j].getItem()) {
							temp++;
							break;
						}
			if(input.length == temp)
				return true;
		}
		return false;
	}
	
	public ItemStack getMajor() { return major; }
	
	public int getOutput() { return output; }
	
	public String getName() { return name; }
	
	public ItemStack[] getInput() { return input; }
}
