package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerShoeElf extends Container {
	
	private InventoryShoeElf invShoeElf;
	
	public ContainerShoeElf(InventoryPlayer invPlayer, InventoryShoeElf invShoeElf) {
		this.invShoeElf = invShoeElf;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return invShoeElf.isUseableByPlayer(playerIn); }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }
}
