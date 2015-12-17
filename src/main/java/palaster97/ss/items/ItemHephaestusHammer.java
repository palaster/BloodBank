package palaster97.ss.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemHephaestusHammer extends ItemModSpecial {

	private final int reuseTime = 12000;
	
	public ItemHephaestusHammer() {
		super();
		setUnlocalizedName("hephaestusHammer");
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("ReuseTimer", 0);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(playerIn.isSneaking()) {
			for(int i = 0; i < playerIn.inventory.mainInventory.length; i++)
				if(playerIn.inventory.mainInventory[i] != null && playerIn.inventory.mainInventory[i].stackSize == 1 && playerIn.inventory.mainInventory[i].getItemDamage() > 0)
					if(!playerIn.inventory.mainInventory[i].equals(itemStackIn))
						playerIn.inventory.mainInventory[i].setItemDamage(0);
			for(int i = 0; i < playerIn.inventory.armorInventory.length; i++)
				if(playerIn.inventory.armorItemInSlot(i) != null && playerIn.inventory.armorItemInSlot(i).stackSize == 1 && playerIn.inventory.armorItemInSlot(i).getItemDamage() > 0)
					playerIn.inventory.armorInventory[i].setItemDamage(0);
			if(!itemStackIn.hasTagCompound())
				itemStackIn.setTagCompound(new NBTTagCompound());
			itemStackIn.getTagCompound().setInteger("ReuseTimer", reuseTime);
		}
		return itemStackIn;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!worldIn.isRemote)
			if(stack.hasTagCompound() && stack.getTagCompound().getInteger("ReuseTimer") > 0)
				stack.getTagCompound().setInteger("ReuseTimer", stack.getTagCompound().getInteger("ReuseTimer") - 1);
	}

	// TODO: Timer doesn't decrement correctly.
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(stack.hasTagCompound() && stack.getTagCompound().getInteger("ReuseTimer") > 0)
			tooltip.add(StatCollector.translateToLocal("ss.misc.reuse") + ": " + (stack.getTagCompound().getInteger("ReuseTime")/20));
	}
}
