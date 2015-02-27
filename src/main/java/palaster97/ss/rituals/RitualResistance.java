package palaster97.ss.rituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class RitualResistance extends Ritual {

	protected RitualResistance(int ritualID, ItemStack stack) {
		super(ritualID, stack);
	}
	
	@Override
	public void activate(World world, int x, int y, int z, EntityPlayer player) {
		if(!world.isRemote)
			player.addPotionEffect(new PotionEffect(11, 200, 2));
	}
}