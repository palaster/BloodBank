package palaster.bb.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.items.ItemLetter;

public class InventoryModLetter extends InventoryMod {

    private final ItemStack stack;

    public InventoryModLetter(ItemStack stack) {
        super(9);
        this.stack = stack;
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        readFromNBT(stack.getTagCompound());
    }

    @Override
    public void markDirty() {
        super.markDirty();
        writeToNBT(stack.getTagCompound());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) { return player.getHeldItem() == stack; }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) { return !(stack.getItem() instanceof ItemLetter); }
}
