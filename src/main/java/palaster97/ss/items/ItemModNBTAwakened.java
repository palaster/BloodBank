package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModNBTAwakened extends ItemModSpecial {

	public ItemModNBTAwakened() { super(); }
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("IsAwakened", false);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(stack.hasTagCompound()) {
			if(stack.getTagCompound().getBoolean("IsAwakened"))
				tooltip.add("Awakened");
		} else
			tooltip.add(StatCollector.translateToLocal("ss.misc.broken"));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(playerIn.isSneaking()) {
				if(!itemStackIn.hasTagCompound())
					itemStackIn.setTagCompound(new NBTTagCompound());
				itemStackIn.getTagCompound().setBoolean("IsAwakened", !itemStackIn.getTagCompound().getBoolean("IsAwakened"));
			}
		}
		return itemStackIn;
	}
}
