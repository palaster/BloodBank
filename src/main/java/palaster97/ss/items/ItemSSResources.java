package palaster97.ss.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.entities.extended.SSExtendedPlayer;
import palaster97.ss.libs.LibMod;

import java.util.List;

public class ItemSSResources extends Item {

    private static String[] names = new String[]{"vHeart", "test"};

    public ItemSSResources() {
        super();
        setCreativeTab(CreativeTabSS.tabSS);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName("ssResources");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!worldIn.isRemote && itemStackIn.getItemDamage() == 0) {
            SSExtendedPlayer props = SSExtendedPlayer.get(playerIn);
            if(props != null && props.getBloodMax() <= 10000) {
                props.setBloodMax(props.getBloodMax() + 250);
                if(itemStackIn.stackSize > 1)
                    itemStackIn.stackSize--;
                else if(itemStackIn.stackSize == 1)
                    return null;
            }
        }
        return itemStackIn;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName() + "." + names[stack.getItemDamage()]; }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        GameRegistry.registerItem(this, unlocalizedName, LibMod.modid);
        for(int i = 0; i < names.length; i++)
            setItemRender(names[i], i);
        return super.setUnlocalizedName(unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void setItemRender(String name, int i) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
