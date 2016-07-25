package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.capabilities.entities.BloodBankCapability.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.api.capabilities.items.IVampiric;

public class ItemBoundBloodBottle extends ItemModSpecial {
	
	public static String tag_UUID = "BoundBloodBottleUUID";

	public ItemBoundBloodBottle() {
		super();
		setMaxDamage(4000);
		setUnlocalizedName("boundBloodBottle");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound()) {
			if(playerIn.worldObj.getPlayerEntityByUUID(stack.getTagCompound().getUniqueId(tag_UUID)) != null)
				tooltip.add(playerIn.worldObj.getPlayerEntityByUUID(stack.getTagCompound().getUniqueId(tag_UUID)).getName());
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			if(stack.getItemDamage() > 0)
				if(stack.hasTagCompound())
					if(worldIn.getPlayerEntityByUUID(stack.getTagCompound().getUniqueId(tag_UUID)) != null) {
						final IBloodBank bloodBank = BloodBankCapabilityProvider.get(worldIn.getPlayerEntityByUUID(stack.getTagCompound().getUniqueId(tag_UUID)));
						if(bloodBank != null)
							if(bloodBank.getMaxBlood() > 0) {
								bloodBank.consumeBlood(1);
								stack.damageItem(-1, worldIn.getPlayerEntityByUUID(stack.getTagCompound().getUniqueId(tag_UUID)));
							}
					}
			if(stack.getItemDamage() < stack.getMaxDamage()) {
				for(int i = 0; i < ((EntityPlayer) entityIn).inventory.getSizeInventory(); i++)
					if(((EntityPlayer) entityIn).inventory.getStackInSlot(i) != null && ((EntityPlayer) entityIn).inventory.getStackInSlot(i).getItem() instanceof IVampiric || ((EntityPlayer) entityIn).inventory.getStackInSlot(i) != null && ((EntityPlayer) entityIn).inventory.getStackInSlot(i).hasTagCompound() && ((EntityPlayer) entityIn).inventory.getStackInSlot(i).getTagCompound().getBoolean(ItemBBResources.tag_hasVampireSigil))
						if(((EntityPlayer) entityIn).inventory.getStackInSlot(i).getItemDamage() > 0) {
							((EntityPlayer) entityIn).inventory.getStackInSlot(i).damageItem(-1, ((EntityPlayer) entityIn));
							stack.damageItem(1, ((EntityPlayer) entityIn));
						}
				for(int i = 0; i < ((EntityPlayer) entityIn).inventory.armorInventory.length; i++)
					if(((EntityPlayer) entityIn).inventory.armorItemInSlot(i) != null && ((EntityPlayer) entityIn).inventory.armorItemInSlot(i).getItem() instanceof IVampiric || ((EntityPlayer) entityIn).inventory.armorItemInSlot(i) != null && ((EntityPlayer) entityIn).inventory.armorItemInSlot(i).hasTagCompound() && ((EntityPlayer) entityIn).inventory.armorItemInSlot(i).getTagCompound().getBoolean(ItemBBResources.tag_hasVampireSigil))
						if(((EntityPlayer) entityIn).inventory.armorItemInSlot(i).getItemDamage() >= 1) {
							((EntityPlayer) entityIn).inventory.armorItemInSlot(i).damageItem(-1, ((EntityPlayer) entityIn));
							stack.damageItem(1, ((EntityPlayer) entityIn));
						}
			}
		}
	}
}
