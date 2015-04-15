package palaster97.ss.rituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import palaster97.ss.ScreamingSouls;

public class RitualSpace extends Ritual {

	protected RitualSpace(int ritualID, ItemStack stack) {
		super(ritualID, stack);
	}
	
	@Override
	public void activate(World world, BlockPos pos, EntityPlayer player) {
		if(!world.isRemote)
			player.openGui(ScreamingSouls.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
	}
}
