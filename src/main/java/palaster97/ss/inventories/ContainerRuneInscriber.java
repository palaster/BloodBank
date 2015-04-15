package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import palaster97.ss.blocks.tile.TileEntityRuneInscriber;

public class ContainerRuneInscriber extends Container {
	
	private TileEntityRuneInscriber ri;
	
	public ContainerRuneInscriber(InventoryPlayer invPlayer, TileEntityRuneInscriber ri) {
		this.ri = ri;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return ri.isUseableByPlayer(playerIn); }
}
