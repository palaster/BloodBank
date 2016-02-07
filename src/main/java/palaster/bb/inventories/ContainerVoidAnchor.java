package palaster.bb.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import palaster.bb.blocks.tile.TileEntityModInventory;

public class ContainerVoidAnchor extends Container {
	
	private TileEntityModInventory va;
	
	public ContainerVoidAnchor(InventoryPlayer invPlayer, TileEntityModInventory va) {
		this.va = va;
		
		int temp = 0;
		
		for(int y = 0; y < 2; y++)
			for(int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(va, temp, 8 + 18 * x, 18 + y * 18));
				temp++;
			}
		
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 126));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 68 + y * 18));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) { return va.isUseableByPlayer(p_75145_1_); }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }
	
	@Override
	protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) { return false; }
}
