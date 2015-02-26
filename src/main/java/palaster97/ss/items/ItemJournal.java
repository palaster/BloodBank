package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemJournal extends ItemModSpecial {

	public ItemJournal() {
		super();
		setUnlocalizedName("journal");
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("Activated", false);
		stack.getTagCompound().setInteger("Level", 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(!stack.hasTagCompound())
			tooltip.add(StatCollector.translateToLocal("ss.misc.broken"));
		else {
			if(stack.getTagCompound().getBoolean("Activated"))
				tooltip.add(StatCollector.translateToLocal("ss.misc.activated"));
			tooltip.add(StatCollector.translateToLocal("ss.misc.level") + ": " + stack.getTagCompound().getInteger("Level"));
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(itemStackIn.hasTagCompound()) {
				if(!playerIn.isSneaking()) {
					if(itemStackIn.getTagCompound().getBoolean("Activated"))
						itemStackIn.getTagCompound().setBoolean("Activated", false);
					else
						itemStackIn.getTagCompound().setBoolean("Activated", true);
				} else {
					if(!itemStackIn.getTagCompound().getBoolean("Activated")) {
						if(itemStackIn.getTagCompound().getInteger("Level") >= 20) {
							EntityXPOrb xp = new EntityXPOrb(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, 20);
							worldIn.spawnEntityInWorld(xp);
							itemStackIn.getTagCompound().setInteger("Level", itemStackIn.getTagCompound().getInteger("Level") - 20);
						} else {
							EntityXPOrb xp = new EntityXPOrb(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, itemStackIn.getTagCompound().getInteger("Level"));
							worldIn.spawnEntityInWorld(xp);
							itemStackIn.getTagCompound().setInteger("Level", 0);
						}
					}
				}
			} else {
				itemStackIn.setTagCompound(new NBTTagCompound());
				itemStackIn.getTagCompound().setInteger("Level", 0);
			}
		}
		return itemStackIn;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		if(stack.hasTagCompound())
			if(stack.getTagCompound().getBoolean("Activated"))
				return true;
		return super.hasEffect(stack); 
	}
}
