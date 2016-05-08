package palaster.bb.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import palaster.bb.api.capabilities.items.IFlameSpell;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.items.BBItems;

public class FlamesSpellRecipe implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack spell = null;
        boolean foundFlames = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null)
                if(stack.getItem() instanceof IFlameSpell)
                    spell = stack;
                else if(stack.isItemEqual(new ItemStack(BBItems.flames)))
                    foundFlames = true;
        }
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null)
                if(stack != spell && !stack.isItemEqual(new ItemStack(BBItems.flames)))
                    return false;
        }
        return spell != null && foundFlames;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack spell = null;
        ItemStack flames = null;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null)
                if(stack.getItem() instanceof IFlameSpell)
                    spell = stack;
                else if(stack.isItemEqual(new ItemStack(BBItems.flames)))
                    flames = stack;
        }
        if(spell == null && flames == null)
            return null;
        ItemStack flamesCopy = flames.copy();
        BBItemStackHelper.setItemStackInsideItemStack(flamesCopy, spell);
        return flamesCopy;
    }

    @Override
    public int getRecipeSize() { return 10; }

    @Override
    public ItemStack getRecipeOutput() { return null; }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) { return ForgeHooks.defaultRecipeGetRemainingItems(inv); }
}
