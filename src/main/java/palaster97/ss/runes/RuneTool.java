package palaster97.ss.runes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class RuneTool extends Rune {

	public RuneTool(int runeID) {
		super(runeID);
	}

	@Override
	public void activate(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
