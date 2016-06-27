package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IVampiric;
import palaster.bb.libs.LibNBT;

public class ItemBloodBottle extends ItemModSpecial {

	public ItemBloodBottle() {
		super();
		setMaxDamage(2000);
		setUnlocalizedName("bloodBottle");
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) { return new ItemStack(Items.GLASS_BOTTLE); }
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entityIn;
			for(int i = 0; i < p.inventory.getSizeInventory(); i++)
				if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() instanceof IVampiric || p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).hasTagCompound() && p.inventory.getStackInSlot(i).getTagCompound().getBoolean(LibNBT.hasVampireSigil))
					if(p.inventory.getStackInSlot(i).getItemDamage() > 0) {
						p.inventory.getStackInSlot(i).damageItem(-1, p);
						stack.damageItem(1, p);
					}
			for(int i = 0; i < p.inventory.armorInventory.length; i++)
				if(p.inventory.armorItemInSlot(i) != null && p.inventory.armorItemInSlot(i).getItem() instanceof IVampiric || p.inventory.armorItemInSlot(i) != null && p.inventory.armorItemInSlot(i).hasTagCompound() && p.inventory.armorItemInSlot(i).getTagCompound().getBoolean(LibNBT.hasVampireSigil))
					if(p.inventory.armorItemInSlot(i).getItemDamage() >= 1) {
						p.inventory.armorItemInSlot(i).damageItem(-1, p);
						stack.damageItem(1, p);
					}
		}
	}
}
