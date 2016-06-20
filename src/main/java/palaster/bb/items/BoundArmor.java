package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.core.helpers.BBItemStackHelper;

public class BoundArmor extends BBArmor {

	public BoundArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
		super(material, renderIndex, entityEquipmentSlot);
		setUnlocalizedName("bound." + armorType);
		setMaxDamage(6000);
		if(entityEquipmentSlot == EntityEquipmentSlot.HEAD)
			MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onItemToss(ItemTossEvent e) {
		if(!e.getPlayer().worldObj.isRemote)
			if(e.getEntityItem().getEntityItem() != null && e.getEntityItem().getEntityItem().getItem() instanceof BoundArmor) {
				if(e.getEntityItem().getEntityItem().getItem() == BBItems.boundHelmet || e.getEntityItem().getEntityItem().getItem() == BBItems.boundChestplate || e.getEntityItem().getEntityItem().getItem() == BBItems.boundLeggings || e.getEntityItem().getEntityItem().getItem() == BBItems.boundBoots)
					if(BBItemStackHelper.getItemStackFromItemStack(e.getEntityItem().getEntityItem()) != null)
						e.getPlayer().worldObj.spawnEntityInWorld(new EntityItem(e.getPlayer().worldObj, e.getPlayer().posX, e.getPlayer().posY, e.getPlayer().posZ, BBItemStackHelper.getItemStackFromItemStack(e.getEntityItem().getEntityItem())));
				e.setCanceled(true);
			}
	}

	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(this == BBItems.boundHelmet || this == BBItems.boundChestplate || this == BBItems.boundBoots)
            return "bb:models/armor/bound_layer_1.png";
        else if(this == BBItems.boundLeggings)
            return "bb:models/armor/bound_layer_2.png";
        return null;
    }
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(!world.isRemote)
            if(itemStack != null)
                if(itemStack.getItem() == BBItems.boundHelmet || itemStack.getItem() == BBItems.boundChestplate || itemStack.getItem() == BBItems.boundLeggings || itemStack.getItem() == BBItems.boundBoots)
                    itemStack.damageItem(1, player);
	}
	
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote)
            if(entityIn instanceof EntityPlayer)
                if(stack != null)
                    if(stack.getItem() == BBItems.boundHelmet || stack.getItem() == BBItems.boundChestplate || stack.getItem() == BBItems.boundLeggings || stack.getItem() == BBItems.boundBoots) {
                        if(itemSlot == 103 || itemSlot == 102 || itemSlot == 101 || itemSlot == 100) {} else
                            removeBoundArmorFromArmor(stack, (EntityPlayer) entityIn, itemSlot);
                    }
    }

    public void removeBoundArmorFromInventory(ItemStack holder, EntityPlayer player, int itemSlot) {
        ItemStack stack = BBItemStackHelper.getItemStackFromItemStack(holder);
        player.inventory.setInventorySlotContents(itemSlot, stack);
    }

    public static void removeBoundArmorFromArmor(ItemStack stack, EntityPlayer player, int itemSlot) {
        ItemStack stack1 = player.inventory.getStackInSlot(itemSlot);
        if(stack1 != null)
            if(stack1.getItem() == BBItems.boundHelmet || stack1.getItem() == BBItems.boundChestplate || stack1.getItem() == BBItems.boundLeggings || stack1.getItem() == BBItems.boundBoots)
                player.inventory.setInventorySlotContents(itemSlot, BBItemStackHelper.getItemStackFromItemStack(stack));
    }
}
