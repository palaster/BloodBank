package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.capabilities.items.ISpecialArmorAbility;
import palaster.bb.entities.effects.BBPotions;
import palaster.bb.libs.LibNBT;

public class SandArmor extends BBArmor implements ISpecialArmorAbility {

	public SandArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
		super(material, renderIndex, entityEquipmentSlot);
		if(entityEquipmentSlot == EntityEquipmentSlot.HEAD)
			MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound() && stack.getTagCompound().getInteger(LibNBT.timer) > 0) {
			int seconds = (stack.getTagCompound().getInteger(LibNBT.timer) / 20) % 60;
		    int totalMinutes = (stack.getTagCompound().getInteger(LibNBT.timer) / 20) / 60;
		    int minutes = totalMinutes % 60;
			tooltip.add(I18n.format("bb.misc.timeLeft") + ": " + minutes + " " + I18n.format("bb.misc.minutes") + " " + seconds + " " + I18n.format("bb.misc.seconds"));
		}
	}
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(this == BBItems.sandHelmet || this == BBItems.sandChestplate || this == BBItems.sandBoots)
            return "bb:models/armor/sand_layer_1.png";
        else if(this == BBItems.sandLeggings)
            return "bb:models/armor/sand_layer_2.png";
        return null;
    }
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(!world.isRemote)
			if(itemStack != null && itemStack.getItem() == BBItems.sandHelmet)
				if(itemStack.hasTagCompound() && itemStack.getTagCompound().getInteger(LibNBT.timer) > 0)
					itemStack.getTagCompound().setInteger(LibNBT.timer, itemStack.getTagCompound().getInteger(LibNBT.timer) - 1);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote)
			if(stack != null && stack.getItem() == BBItems.sandHelmet)
				if(stack.hasTagCompound() && stack.getTagCompound().getInteger(LibNBT.timer) > 0)
					stack.getTagCompound().setInteger(LibNBT.timer, stack.getTagCompound().getInteger(LibNBT.timer) - 1);
	}

	@Override
	public void doArmorAbility(World worldObj, EntityPlayer player) {
		if(!worldObj.isRemote)
			if(player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].getItem() instanceof SandArmor)
				if(!player.inventory.armorInventory[3].hasTagCompound() || player.inventory.armorInventory[3].getTagCompound().getInteger(LibNBT.timer) <= 0)
					if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof SandArmor)
						if(player.inventory.armorInventory[1] != null && player.inventory.armorInventory[1].getItem() instanceof SandArmor)
							if(player.inventory.armorInventory[0] != null && player.inventory.armorInventory[0].getItem() instanceof SandArmor) {
								player.addPotionEffect(new PotionEffect(BBPotions.sandBody, 600, 0, false, true));
								if(!player.inventory.armorInventory[3].hasTagCompound())
									player.inventory.armorInventory[3].setTagCompound(new NBTTagCompound());
								player.inventory.armorInventory[3].getTagCompound().setInteger(LibNBT.timer, 12000);
							}
	}
}
