package palaster.bb.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class ItemModArmor extends ItemArmor {

    public ItemModArmor(ItemArmor.ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
        super(material, renderIndex, entityEquipmentSlot);
        setCreativeTab(CreativeTabBB.tabBB);
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        setRegistryName(new ResourceLocation(LibMod.modid, unlocalizedName));
        GameRegistry.register(this);
        return super.setUnlocalizedName(LibMod.modid + ":" + unlocalizedName);
    }
}
