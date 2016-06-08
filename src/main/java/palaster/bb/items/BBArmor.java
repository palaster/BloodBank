package palaster.bb.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class BBArmor extends ItemArmor {

    public BBArmor(ItemArmor.ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
        super(material, renderIndex, entityEquipmentSlot);
        setCreativeTab(CreativeTabBB.tabBB);
        setUnlocalizedName(material.getName() + "." + armorType);
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        setRegistryName(new ResourceLocation(LibMod.modid, unlocalizedName));
        GameRegistry.register(this);
        setCustomModelResourceLocation();
        return super.setUnlocalizedName(LibMod.modid + ":" + unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void setCustomModelResourceLocation() { ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory")); }

    
}
