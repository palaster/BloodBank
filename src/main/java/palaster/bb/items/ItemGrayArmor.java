package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import palaster.bb.api.capabilities.items.ISpecialArmorAbility;

public class ItemGrayArmor extends ItemModArmor implements ISpecialArmorAbility {
	
	public static String nbtTagShoutHolder = "ShoutHolder";
	public static String nbtTimer = "Timer";

	public ItemGrayArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
		super(material, renderIndex, entityEquipmentSlot);
		setUnlocalizedName("gray." + armorType);
		if(entityEquipmentSlot == EntityEquipmentSlot.HEAD)
			MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(this == BBItems.grayHelmet || this == BBItems.grayChestplate || this == BBItems.grayBoots)
            return "bb:models/armor/gray_layer_1.png";
        else if(this == BBItems.grayLeggings)
            return "bb:models/armor/gray_layer_2.png";
        return null;
    }

	@Override
	public void doArmorAbility(World worldObj, EntityPlayer player) {}
}
