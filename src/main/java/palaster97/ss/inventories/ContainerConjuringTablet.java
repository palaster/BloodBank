package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import palaster97.ss.blocks.tile.TileEntityConjuringTablet;

public class ContainerConjuringTablet extends Container {
	
	private TileEntityConjuringTablet ct;
	
	public ContainerConjuringTablet(InventoryPlayer invPlayer, TileEntityConjuringTablet ct) {
		this.ct = ct;
		
		addSlotToContainer(new Slot(ct, 0, 81, 47));
		addSlotToContainer(new Slot(ct, 1, 81, 11));
		addSlotToContainer(new Slot(ct, 2, 37, 40));
		addSlotToContainer(new Slot(ct, 3, 51, 83));
		addSlotToContainer(new Slot(ct, 4, 111, 83));
		addSlotToContainer(new Slot(ct, 5, 125, 40));
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 169));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 111 + y * 18));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return ct.isUseableByPlayer(playerIn); }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) { return null; }
}