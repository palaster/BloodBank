package palaster97.ss.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBoundedPlayers extends Container {

    private InventoryBoundedPlayers bp;

    public ContainerBoundedPlayers(InventoryBoundedPlayers boundedPlayers) {
        this.bp = boundedPlayers;

        for(int y = 0; y < 3; y++)
            for(int x = 0; x < 9; x++)
                addSlotToContainer(new Slot(boundedPlayers, x + y * 9, 8 + 18 * x, 84 + y * 18));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) { return bp.isUseableByPlayer(playerIn); }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }

    @Override
    protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) { return false; }
}
