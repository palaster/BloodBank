package palaster.bb.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.libs.LibMod;

public class BBArmor extends ItemArmor {

    public BBArmor(ItemArmor.ArmorMaterial material, int renderIndex, EntityEquipmentSlot entityEquipmentSlot) {
        super(material, renderIndex, entityEquipmentSlot);
        setCreativeTab(CreativeTabBB.tabSS);
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
        GameRegistry.registerItem(this, unlocalizedName);
        return super.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        if(!world.isRemote)
            if(itemstack != null && itemstack.getItem() instanceof BBArmor)
                if(itemstack.getItem() == BBItems.boundHelmet || itemstack.getItem() == BBItems.boundChestplate || itemstack.getItem() == BBItems.boundLeggings || itemstack.getItem() == BBItems.boundBoots)
                    if(BBItemStackHelper.getItemStackFromItemStack(itemstack) != null)
                        return new EntityItem(world, location.posX, location.posY, location.posZ, BBItemStackHelper.getItemStackFromItemStack(itemstack));
        return null;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote)
            if(entityIn instanceof EntityPlayer) {
                if(this == BBItems.boundHelmet) {
                    if(itemSlot == 103)
                        stack.damageItem(1, (EntityPlayer) entityIn);
                    else
                        removeBoundArmorFromInventory(stack, (EntityPlayer) entityIn, itemSlot);
                }
                if(this == BBItems.boundChestplate) {
                    if(itemSlot == 102)
                        stack.damageItem(1, (EntityPlayer) entityIn);
                    else
                        removeBoundArmorFromInventory(stack, (EntityPlayer) entityIn, itemSlot);
                }
                if(this == BBItems.boundLeggings) {
                    if(itemSlot == 101)
                        stack.damageItem(1, (EntityPlayer) entityIn);
                    else
                        removeBoundArmorFromInventory(stack, (EntityPlayer) entityIn, itemSlot);
                }
                if(this == BBItems.boundBoots) {
                    if(itemSlot == 100)
                        stack.damageItem(1, (EntityPlayer) entityIn);
                    else
                        removeBoundArmorFromInventory(stack, (EntityPlayer) entityIn, itemSlot);
                }
            }
    }

    public void removeBoundArmorFromInventory(ItemStack holder, EntityPlayer player, int itemSlot) {
        ItemStack stack = BBItemStackHelper.getItemStackFromItemStack(holder);
        player.inventory.setInventorySlotContents(itemSlot, stack);
    }

    public static void removeBoundArmorFromArmor(ItemStack holder, EntityPlayer player, int itemSlot) { player.inventory.armorInventory[itemSlot] = BBItemStackHelper.getItemStackFromItemStack(holder); }

    @SideOnly(Side.CLIENT)
    public void setItemRender(String name) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
