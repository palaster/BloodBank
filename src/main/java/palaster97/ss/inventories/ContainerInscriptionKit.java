package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerInscriptionKit extends Container {
	
	private InventoryInscriptionKit ik;
	
	public ContainerInscriptionKit(InventoryPlayer invPlayer, InventoryInscriptionKit ik) {
		this.ik = ik;
		
		addSlotToContainer(new Slot(ik, 2, 80, 35));
		
		for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return ik.isUseableByPlayer(playerIn); }
}
