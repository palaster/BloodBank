package palaster.bb.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerRPGIntro extends Container {
	
	public ContainerRPGIntro() {}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return true; }	
}
