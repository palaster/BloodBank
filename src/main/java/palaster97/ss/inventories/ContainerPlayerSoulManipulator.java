package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.inventories.slots.SlotPSM;

public class ContainerPlayerSoulManipulator extends Container {
	
	private TileEntityPlayerSoulManipulator psm;
	
	public ContainerPlayerSoulManipulator(InventoryPlayer invPlayer, TileEntityPlayerSoulManipulator psm) {
		this.psm = psm;
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
	
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
		
		addSlotToContainer(new SlotPSM(psm, 0, 55, 35));
		addSlotToContainer(new SlotPSM(psm, 1, 85, 35));
		addSlotToContainer(new SlotPSM(psm, 2, 115, 35));
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) { return psm.isUseableByPlayer(p_75145_1_); }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) { return null; }
	
	@Override
	protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) { return false; }
}
