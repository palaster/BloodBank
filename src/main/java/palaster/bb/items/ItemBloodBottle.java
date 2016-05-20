package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IVampiric;

public class ItemBloodBottle extends ItemModSpecial {

	public ItemBloodBottle() {
		super();
		setMaxDamage(256);
		setUnlocalizedName("bloodBottle");
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entityIn;
			for(int i = 0; i < p.inventory.getSizeInventory(); i++)
				if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() instanceof IVampiric || p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).hasTagCompound() && p.inventory.getStackInSlot(i).getTagCompound().getBoolean("HasVampireSigil"))
					if(p.inventory.getStackInSlot(i).getItemDamage() > 1) {
						p.inventory.getStackInSlot(i).setItemDamage(p.inventory.getStackInSlot(i).getItemDamage() - 1);
						stack.damageItem(1, p);
					} else if(p.inventory.getStackInSlot(i).getItemDamage() <= 1) {
						p.inventory.setInventorySlotContents(i, new ItemStack(Items.GLASS_BOTTLE));
						stack.damageItem(1, p);
					}
			for(int i = 0; i < p.inventory.armorInventory.length; i++)
				if(p.inventory.armorItemInSlot(i) != null && p.inventory.armorItemInSlot(i).getItem() instanceof IVampiric || p.inventory.armorItemInSlot(i) != null && p.inventory.armorItemInSlot(i).hasTagCompound() && p.inventory.armorItemInSlot(i).getTagCompound().getBoolean("HasVampireSigil"))
					if(p.inventory.armorItemInSlot(i).getItemDamage() > 1) {
						p.inventory.armorItemInSlot(i).setItemDamage(p.inventory.armorItemInSlot(i).getItemDamage() - 1);
						stack.damageItem(1, p);
					} else if(p.inventory.getStackInSlot(i).getItemDamage() <= 1) {
						p.inventory.setInventorySlotContents(i, new ItemStack(Items.GLASS_BOTTLE));
						stack.damageItem(1, p);
					}
		}
	}
}
