package palaster97.ss.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.libs.LibMod;

public class ItemModMeta extends Item {

    public ItemModMeta() {
        super();
        setCreativeTab(CreativeTabSS.tabSS);
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        GameRegistry.registerItem(this, unlocalizedName, LibMod.modid);
        setItemRender(unlocalizedName, 0);
        return super.setUnlocalizedName(unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void setItemRender(String name, int i) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
