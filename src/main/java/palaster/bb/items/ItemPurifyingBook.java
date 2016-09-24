package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IPurified;

public class ItemPurifyingBook extends ItemModSpecial {

	public ItemPurifyingBook(String unlocalizedName) { super(unlocalizedName, 2000); }
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) { return new ItemStack(Items.BOOK); }
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer)
			if(stack.getItemDamage() < stack.getMaxDamage()) {
				for(int i = 0; i < ((EntityPlayer) entityIn).inventory.getSizeInventory(); i++)
					if(((EntityPlayer) entityIn).inventory.getStackInSlot(i) != null && ((EntityPlayer) entityIn).inventory.getStackInSlot(i).getItem() instanceof IPurified)
						if(((EntityPlayer) entityIn).inventory.getStackInSlot(i).getItemDamage() > 0) {
							((EntityPlayer) entityIn).inventory.getStackInSlot(i).damageItem(-1, ((EntityPlayer) entityIn));
							stack.damageItem(1, ((EntityPlayer) entityIn));
						}
				for(int i = 0; i < ((EntityPlayer) entityIn).inventory.armorInventory.length; i++)
					if(((EntityPlayer) entityIn).inventory.armorInventory[i] != null && ((EntityPlayer) entityIn).inventory.armorInventory[i].getItem() instanceof IPurified)
						if(((EntityPlayer) entityIn).inventory.armorInventory[i].getItemDamage() >= 1) {
							((EntityPlayer) entityIn).inventory.armorInventory[i].damageItem(-1, ((EntityPlayer) entityIn));
							stack.damageItem(1, ((EntityPlayer) entityIn));
						}
			}
	}
}
