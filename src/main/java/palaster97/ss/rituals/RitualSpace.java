package palaster97.ss.rituals;

import palaster97.ss.ScreamingSouls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RitualSpace extends Ritual {

	protected RitualSpace(int ritualID, ItemStack stack) {
		super(ritualID, stack);
	}
	
	@Override
	public void activate(World world, int x, int y, int z, EntityPlayer player) {
		if(!world.isRemote)
			player.openGui(ScreamingSouls.instance, 1, world, x, y, z);
	}
}
