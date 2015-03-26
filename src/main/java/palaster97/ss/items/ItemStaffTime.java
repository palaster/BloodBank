package palaster97.ss.items;

import palaster97.ss.core.helpers.SSPlayerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStaffTime extends ItemModSpecial {

	public ItemStaffTime() {
		super();
		setUnlocalizedName("staffTime");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote)
			if(playerIn.inventory.hasItem(SSItems.journal))
				if(SSPlayerHelper.getJournalAmount(playerIn) >= 100)
					if(SSPlayerHelper.reduceJournalAmount(playerIn, 100)) {
						worldIn.updateEntity(playerIn);
						worldIn.provider.setWorldTime(worldIn.provider.getWorldTime() + 12000);
					}
		return itemStackIn;
	}
}
