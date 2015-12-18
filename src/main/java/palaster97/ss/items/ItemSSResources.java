package palaster97.ss.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSSResources extends ItemModMeta {

    private static String[] names = new String[]{"vHeart", "test"};

    public ItemSSResources() {
        super();
        setHasSubtypes(true);
        setUnlocalizedName("ssResources");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName() + "." + names[stack.getItemDamage()]; }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }
}
