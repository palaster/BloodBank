package palaster97.ss.rituals;

import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public abstract class RitualActive extends Ritual {

	public final int length;
	public BlockPos ritualPos;
	
	protected RitualActive(int ritualID, ItemStack stack, int length) {
		super(ritualID, stack);
		this.length = length;
	}	
}
