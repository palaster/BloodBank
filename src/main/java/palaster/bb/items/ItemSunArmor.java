package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import palaster.bb.api.capabilities.items.IPurified;

public class ItemSunArmor extends ItemModArmor implements IPurified {

	public ItemSunArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
		super(material, renderIndex, entityEquipmentSlot);
		setUnlocalizedName("sun." + entityEquipmentSlot);
	}
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(this == BBItems.sunHelmet || this == BBItems.sunChestplate || this == BBItems.sunBoots)
            return "bb:models/armor/sun_layer_1.png";
        else if(this == BBItems.sunLeggings)
            return "bb:models/armor/sun_layer_2.png";
        return null;
    }
}
