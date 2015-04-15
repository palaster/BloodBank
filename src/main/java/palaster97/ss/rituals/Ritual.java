package palaster97.ss.rituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class Ritual {
	
	public static final Ritual[] rituals = new Ritual[256];
	
	public static final Ritual space = new RitualSpace(0, new ItemStack(Blocks.chest, 1, 0));
	public static final Ritual resistance = new RitualResistance(1, new ItemStack(Items.iron_ingot, 1, 0));

	public final int ritualID;
	public final ItemStack stack;
	
	protected Ritual(int ritualID, ItemStack stack) {
		this.ritualID = ritualID;
		this.stack = stack;
		if(rituals[ritualID] != null)
            throw new IllegalArgumentException("Duplicate ritual id! " + getClass() + " and " + rituals[ritualID].getClass() + " Ritual ID:" + ritualID);
        else
        	rituals[ritualID] = this;
	}

	public abstract void activate(World world, BlockPos pos, EntityPlayer player);
}
