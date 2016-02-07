package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemHephaestusHammer extends ItemModSpecial {
	
	public ItemHephaestusHammer() {
		super();
		setMaxDamage(12000);
		setUnlocalizedName("hephaestusHammer");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote && playerIn.isSneaking()) {
			for(int i = 0; i < playerIn.inventory.mainInventory.length; i++)
				if(playerIn.inventory.mainInventory[i] != null && playerIn.inventory.mainInventory[i].stackSize == 1 && playerIn.inventory.mainInventory[i].getItemDamage() > 0)
					if(!playerIn.inventory.mainInventory[i].equals(itemStackIn))
						playerIn.inventory.mainInventory[i].setItemDamage(0);
			for(int i = 0; i < playerIn.inventory.armorInventory.length; i++)
				if(playerIn.inventory.armorItemInSlot(i) != null && playerIn.inventory.armorItemInSlot(i).stackSize == 1 && playerIn.inventory.armorItemInSlot(i).getItemDamage() > 0)
					playerIn.inventory.armorInventory[i].setItemDamage(0);
			itemStackIn.setItemDamage(getMaxDamage());
		}
		return itemStackIn;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote)
			if(stack.getItemDamage() > 0)
				stack.setItemDamage(stack.getItemDamage() - 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(stack.getItemDamage() > 0)
			tooltip.add(StatCollector.translateToLocal("bb.misc.reuse") + ": " + stack.getItemDamage()/20 + " seconds.");
	}
}
