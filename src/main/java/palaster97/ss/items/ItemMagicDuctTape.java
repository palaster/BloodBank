package palaster97.ss.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMagicDuctTape extends ItemModSpecial {

	public ItemMagicDuctTape() {
		super();
		setMaxDamage(256);
		setUnlocalizedName("magicDuctTape");
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entityIn;
			for(int i = 0; i < p.inventory.getSizeInventory(); i++) {
				if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() instanceof IDuctTapeable || p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).hasTagCompound() && p.inventory.getStackInSlot(i).getTagCompound().getBoolean("HasTapeHeart"))
					if(p.inventory.getStackInSlot(i).getItemDamage() > 0) {
						p.inventory.getStackInSlot(i).setItemDamage(p.inventory.getStackInSlot(i).getItemDamage() - 1);
						stack.damageItem(1, p);
					}
			}
			for(int i = 0; i < p.inventory.armorInventory.length; i++) {
				if(p.inventory.armorItemInSlot(i) != null && p.inventory.armorItemInSlot(i).getItem() instanceof IDuctTapeable || p.inventory.armorItemInSlot(i) != null && p.inventory.armorItemInSlot(i).hasTagCompound() && p.inventory.armorItemInSlot(i).getTagCompound().getBoolean("HasTapeHeart"))
					if(p.inventory.armorItemInSlot(i).getItemDamage() > 0) {
						p.inventory.armorItemInSlot(i).setItemDamage(p.inventory.armorItemInSlot(i).getItemDamage() - 1);
						stack.damageItem(1, p);
					}
			}
		}
	}
}
