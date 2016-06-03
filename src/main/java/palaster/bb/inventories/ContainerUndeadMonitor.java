package palaster.bb.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerUndeadMonitor extends Container {

    public ContainerUndeadMonitor(EntityPlayer player) {}

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) { return true; }
}
