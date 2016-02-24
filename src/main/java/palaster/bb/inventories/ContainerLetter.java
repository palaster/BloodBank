package palaster.bb.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLetter extends Container {

    public ContainerLetter(InventoryPlayer invPlayer, InventoryMod invMod) {

        for(int x = 0; x < 9; x++)
            addSlotToContainer(new Slot(invMod, x, 8 + 18 * x, 18));

        for(int x = 0; x < 9; x++)
            addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 108));
        for(int y = 0; y < 3; y++)
            for(int x = 0; x < 9; x++)
                addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 50 + y * 18));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) { return true; }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) { return null; }

    @Override
    protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) { return false; }
}
