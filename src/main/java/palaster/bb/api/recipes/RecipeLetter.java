package palaster.bb.api.recipes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class RecipeLetter {

    ItemStack output;
    Object[] input;

    public RecipeLetter(ItemStack output, Object... input) {
        this.output = output;
        this.input = input;
    }

    public boolean matches(ItemStack... temp) {
        if(temp != null && input != null) {
            List<ItemStack> stacks = new LinkedList<ItemStack>(Arrays.asList(temp));
            Iterator<ItemStack> it2 = stacks.iterator();
            if(stacks.size() == input.length) {
                for(int j = 0; j < input.length; j++) {
                    Object obj = input[j];
                    if(!(obj instanceof ItemStack) && obj instanceof List)
                        for(ItemStack stack : (List<ItemStack>) obj)
                            while(it2.hasNext()) {
                                ItemStack tempStack = it2.next();
                                for(int i = 0; i < stacks.size(); i++)
                                    if(tempStack.getItem() == stack.getItem() && stack.stackSize == tempStack.stackSize) {
                                        it2.remove();
                                        break;
                                    }
                            }
                }
                Iterator<ItemStack> it = stacks.iterator();
                while(it.hasNext()) {
                    ItemStack stack = it.next();
                    for(int i = 0; i < input.length; i++)
                        if(input[i] != null && input[i] instanceof ItemStack)
                            if(ItemStack.areItemsEqual(stack, (ItemStack) input[i]) && ((ItemStack) input[i]).stackSize == stack.stackSize) {
                                it.remove();
                                break;
                            }
                }
            }
            return stacks.isEmpty();
        }
        return false;
    }

    public Object[] getInput() { return input; }

    public ItemStack getOutput() { return output; }
}
