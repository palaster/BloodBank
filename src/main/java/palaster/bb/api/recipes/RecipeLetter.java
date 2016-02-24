package palaster.bb.api.recipes;

import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RecipeLetter {

    ItemStack output;
    ItemStack[] input;

    public RecipeLetter(ItemStack output, ItemStack... input) {
        this.output = output;
        this.input = input;
    }

    public boolean matches(ItemStack... temp) {
        if(temp != null && input != null) {
            List<ItemStack> stacks = new LinkedList<ItemStack>(Arrays.asList(temp));
            Iterator<ItemStack> it = stacks.iterator();
            if(stacks.size() == input.length)
                while(it.hasNext()) {
                    ItemStack stack = it.next();
                    for(int i = 0; i < input.length; i++)
                        if(ItemStack.areItemsEqual(stack, input[i]) && input[i].stackSize == stack.stackSize) {
                            it.remove();
                            break;
                        }
                }
            return stacks.isEmpty();
        }
        return false;
    }

    public ItemStack[] getInput() { return input; }

    public ItemStack getOutput() { return output; }
}
