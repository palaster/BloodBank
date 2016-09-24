package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IVampiric;

public class ItemBloodBottle extends ItemModSpecial {

	public ItemBloodBottle(String unlocalizedName) { super(unlocalizedName, 2000); }
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) { return new ItemStack(Items.GLASS_BOTTLE); }
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer)
			if(stack.getItemDamage() < stack.getMaxDamage()) {
				for(int i = 0; i < ((EntityPlayer) entityIn).inventory.getSizeInventory(); i++)
					if(((EntityPlayer) entityIn).inventory.getStackInSlot(i) != null && ((EntityPlayer) entityIn).inventory.getStackInSlot(i).getItem() instanceof IVampiric || ((EntityPlayer) entityIn).inventory.getStackInSlot(i) != null && ((EntityPlayer) entityIn).inventory.getStackInSlot(i).hasTagCompound() && ((EntityPlayer) entityIn).inventory.getStackInSlot(i).getTagCompound().getBoolean(ItemBBResources.TAG_BOOLEAN_VAMPIRE_SIGIL))
						if(((EntityPlayer) entityIn).inventory.getStackInSlot(i).getItemDamage() > 0) {
							((EntityPlayer) entityIn).inventory.getStackInSlot(i).damageItem(-1, ((EntityPlayer) entityIn));
							stack.damageItem(1, ((EntityPlayer) entityIn));
						}
				for(int i = 0; i < ((EntityPlayer) entityIn).inventory.armorInventory.length; i++)
					if(((EntityPlayer) entityIn).inventory.armorInventory[i] != null && ((EntityPlayer) entityIn).inventory.armorInventory[i].getItem() instanceof IVampiric || ((EntityPlayer) entityIn).inventory.armorInventory[i] != null && ((EntityPlayer) entityIn).inventory.armorInventory[i].hasTagCompound() && ((EntityPlayer) entityIn).inventory.armorInventory[i].getTagCompound().getBoolean(ItemBBResources.TAG_BOOLEAN_VAMPIRE_SIGIL))
						if(((EntityPlayer) entityIn).inventory.armorInventory[i].getItemDamage() >= 1) {
							((EntityPlayer) entityIn).inventory.armorInventory[i].damageItem(-1, ((EntityPlayer) entityIn));
							stack.damageItem(1, ((EntityPlayer) entityIn));
						}
			}
	}
}
