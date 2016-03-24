package palaster.bb.blocks.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import palaster.bb.items.ItemPlayerBinder;

public class TileEntityBloodHeater extends TileEntityModInventory implements ITickable {

    public TileEntityBloodHeater() { super(1); }

    @Override
    public String getName() { return "container.bloodHeater"; }

    @Override
    public void update() {
        if(!worldObj.isRemote)
            if(getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemPlayerBinder) {}
    }

    public static boolean canFurnaceSmelt(TileEntityFurnace furnace) {
        if(furnace.getStackInSlot(0) == null)
            return false;
        else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(furnace.getStackInSlot(0));
            if(itemstack == null)
                return false;
            if(furnace.getStackInSlot(2) == null)
                return true;
            if(!furnace.getStackInSlot(2).isItemEqual(itemstack))
                return false;
            int result = furnace.getStackInSlot(2).stackSize + itemstack.stackSize;
            return result <= 64 && result <= itemstack.getMaxStackSize();
        }
    }
}
