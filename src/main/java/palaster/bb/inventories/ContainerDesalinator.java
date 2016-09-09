package palaster.bb.inventories;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import palaster.bb.blocks.tile.TileEntityDesalinator;

public class ContainerDesalinator extends Container {
	
	private TileEntityDesalinator d;
	
	public ContainerDesalinator(InventoryPlayer invPlayer, TileEntityDesalinator d) {
		this.d = d;

		addSlotToContainer(new SlotItemHandler(d.getItemHandler(), 0, 44, 18));
		addSlotToContainer(new SlotItemHandler(d.getItemHandler(), 1, 80, 18));
		addSlotToContainer(new SlotItemHandler(d.getItemHandler(), 2, 116, 18));
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 108));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 50 + y * 18));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return playerIn.getDistanceSq(d.getPos()) <= 64; }
	
	@Override
	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }
}
