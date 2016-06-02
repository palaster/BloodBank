package palaster.bb.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.libs.LibMod;

public class BBArmor extends ItemArmor {

    public BBArmor(ItemArmor.ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
        super(material, renderIndex, entityEquipmentSlot);
        setCreativeTab(CreativeTabBB.tabBB);
        setUnlocalizedName(material.getName() + "." + armorType);
        setMaxDamage(6000);
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
    public Item setUnlocalizedName(String unlocalizedName) {
        setRegistryName(new ResourceLocation(LibMod.modid, unlocalizedName));
        GameRegistry.register(this);
        setCustomModelResourceLocation();
        return super.setUnlocalizedName(LibMod.modid + ":" + unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void setCustomModelResourceLocation() { ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory")); }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote)
            if(entityIn instanceof EntityPlayer) {
                if(stack != null) {
                    if(stack.getItem() == BBItems.boundHelmet || stack.getItem() == BBItems.boundChestplate || stack.getItem() == BBItems.boundLeggings || stack.getItem() == BBItems.boundBoots) {
                        if(itemSlot == 103 || itemSlot == 102 || itemSlot == 101 || itemSlot == 100) {
                            if(stack.getItem() == BBItems.boundHelmet)
                                stack.damageItem(1, (EntityPlayer) entityIn);
                            else if(stack.getItem() == BBItems.boundChestplate)
                                stack.damageItem(1, (EntityPlayer) entityIn);
                            else if(stack.getItem() == BBItems.boundLeggings)
                                stack.damageItem(1, (EntityPlayer) entityIn);
                            else if(stack.getItem() == BBItems.boundBoots)
                                stack.damageItem(1, (EntityPlayer) entityIn);
                        } else
                            removeBoundArmorFromArmor(stack, (EntityPlayer) entityIn, itemSlot);
                    }
                }
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
