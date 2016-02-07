package palaster.bb.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.inventories.slots.SlotSCCrafting;
import palaster.bb.recipes.SoulCompressorCraftingManager;

public class ContainerSoulCompressor extends Container {

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private BlockPos field_178145_h;

    public ContainerSoulCompressor(InventoryPlayer p_i45800_1_, World worldIn, BlockPos p_i45800_3_) {
    	worldObj = worldIn;
        field_178145_h = p_i45800_3_;
        addSlotToContainer(new SlotSCCrafting(p_i45800_1_.player, craftMatrix, craftResult, 0, 124, 35));
        int i;
        int j;

        for(i = 0; i < 3; ++i)
            for(j = 0; j < 3; ++j)
                addSlotToContainer(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
        for(i = 0; i < 3; ++i)
            for(j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(p_i45800_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for(i = 0; i < 9; ++i)
            addSlotToContainer(new Slot(p_i45800_1_, i, 8 + i * 18, 142));
        onCraftMatrixChanged(craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_) {
    	if(CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj) != null) {
    		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
    	} else
    		craftResult.setInventorySlotContents(0, SoulCompressorCraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        if(!worldObj.isRemote) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemstack = craftMatrix.removeStackFromSlot(i);
                if(itemstack != null)
                    p_75134_1_.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) { return worldObj.getBlockState(field_178145_h).getBlock() != BBBlocks.soulCompressor ? false : p_75145_1_.getDistanceSq((double)field_178145_h.getX() + 0.5D, (double)field_178145_h.getY() + 0.5D, (double)field_178145_h.getZ() + 0.5D) <= 64.0D; }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index == 0) {
                if(!mergeItemStack(itemstack1, 10, 46, true))
                    return null;
                slot.onSlotChange(itemstack1, itemstack);
            } else if(index >= 10 && index < 37) {
                if(!mergeItemStack(itemstack1, 37, 46, false))
                    return null;
            } else if(index >= 37 && index < 46) {
                if(!mergeItemStack(itemstack1, 10, 37, false))
                    return null;
            } else if(!mergeItemStack(itemstack1, 10, 46, false))
                return null;
            if(itemstack1.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();
            if(itemstack1.stackSize == itemstack.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, itemstack1);
        }
        return itemstack;
    }

    @Override
    public boolean canMergeSlot(ItemStack p_94530_1_, Slot p_94530_2_) { return p_94530_2_.inventory != craftResult && super.canMergeSlot(p_94530_1_, p_94530_2_); }
}
