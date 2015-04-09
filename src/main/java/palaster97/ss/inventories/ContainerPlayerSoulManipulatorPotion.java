package palaster97.ss.inventories;

import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.inventories.slots.SlotPSMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPlayerSoulManipulatorPotion extends Container {
	
	private TileEntityPlayerSoulManipulator psm;

	public ContainerPlayerSoulManipulatorPotion(InventoryPlayer invPlayer, TileEntityPlayerSoulManipulator psm) {
		this.psm = psm;
		addSlotToContainer(new SlotPSMP(psm, 2, 80, 35));
		for (int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return psm.isUseableByPlayer(playerIn); }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) { return false; }
}
