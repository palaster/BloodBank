package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.items.SSItems;

public class ContainerPlayerSoulManipulatorInventory extends Container {
	
	private TileEntityPlayerSoulManipulator psm;
	
	public ContainerPlayerSoulManipulatorInventory(InventoryPlayer invPlayer, TileEntityPlayerSoulManipulator psm) {
		this.psm = psm;
		
		ItemStack soul = psm.getStackInSlot(1);
		if(soul != null)
			if(soul.hasTagCompound() && soul.getItem() == SSItems.souls) {
				EntityPlayer p = SSPlayerHelper.getPlayerFromDimensions(soul.getTagCompound().getString("PlayerUUID"));
				if(p != null) {
					for(int x = 0; x < 9; x++)
						addSlotToContainer(new Slot(p.inventory, x, 8 + 18 * x, 76));
					for(int y = 0; y < 3; y++)
						for(int x = 0; x < 9; x++)
							addSlotToContainer(new Slot(p.inventory, x + y * 9 + 9, 8 + 18 * x, 18 + y * 18));
				}
			}
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 166));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 108 + y * 18));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return psm.isUseableByPlayer(playerIn); }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) { return false; }
}
