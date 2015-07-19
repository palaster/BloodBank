package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import palaster97.ss.blocks.tile.TileEntityPlayerManipulator;
import palaster97.ss.inventories.slots.SlotPSM;

public class ContainerPlayerSoulManipulator extends Container {
	
	private TileEntityPlayerManipulator psm;
	
	public ContainerPlayerSoulManipulator(InventoryPlayer invPlayer, TileEntityPlayerManipulator psm) {
		this.psm = psm;
		
		addSlotToContainer(new SlotPSM(psm, 0, 55, 34));
		addSlotToContainer(new SlotPSM(psm, 1, 115, 34));
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) { return psm.isUseableByPlayer(p_75145_1_); }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }
	
	@Override
	protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) { return false; }
}
