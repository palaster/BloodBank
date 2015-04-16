package palaster97.ss.runes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class Rune {

	public static final Rune[] runes = new Rune[256];
	public final int runeID;
	public final ItemStack id;
	
	public static final Rune milk = new RuneMilk(0, new ItemStack(Items.milk_bucket));
	
	public Rune(int runeID, ItemStack id) {
		this.runeID = runeID;
		this.id = id;
		if(runes[runeID] != null)
            throw new IllegalArgumentException("Duplicate ritual id! " + getClass() + " and " + runes[runeID].getClass() + " Rune ID:" + runeID);
        else
        	runes[runeID] = this;
	}
	
	public abstract void activate(World world, BlockPos pos, EntityPlayer player);
}
