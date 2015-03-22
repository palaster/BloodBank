package palaster97.ss.core.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.items.SSItems;

public class SSFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.getItem() == SSItems.coreFuel && fuel.hasTagCompound()) {
			EntityPlayer p = SSPlayerHelper.getPlayerFromDimensions(fuel.getTagCompound().getString("PlayerUUID"));
			if(p != null)
				if(SSPlayerHelper.getJournalAmount(p) >= 20)
					if(SSPlayerHelper.reduceJournalAmount(p, 20))
						return 1600;
		}
		return 0;
	}
}
