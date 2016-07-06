package palaster.bb.inventories;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import palaster.bb.blocks.tile.TileEntityVoidAnchor;

public class ContainerVoidAnchor extends Container {
	
	private TileEntityVoidAnchor va;
	
	public ContainerVoidAnchor(InventoryPlayer invPlayer, TileEntityVoidAnchor va) {
		this.va = va;
		
		int temp = 0;
		
		for(int y = 0; y < 2; y++)
			for(int x = 0; x < 9; x++) {
				addSlotToContainer(new SlotItemHandler(va.getItemHandler(), temp, 8 + 18 * x, 18 + y * 18));
				temp++;
			}
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 126));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 68 + y * 18));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return playerIn.getDistanceSq(va.getPos()) <= 64; }
	
	@Override
	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }
}
