package palaster97.ss.runes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class RuneMilk extends Rune {

	public RuneMilk(int runeID, ItemStack stack) {
		super(runeID, stack);
	}

	@Override
	public void activate(World world, BlockPos pos, EntityPlayer player) {
		if(!world.isRemote)
			player.curePotionEffects(new ItemStack(Items.milk_bucket));
	}
}
