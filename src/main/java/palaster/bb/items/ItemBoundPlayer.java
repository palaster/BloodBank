package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBoundPlayer extends ItemModSpecial {
	
	public static String tag_UUID = "BoundPlayerUUID";

	public ItemBoundPlayer() {
		super();
		setUnlocalizedName("boundPlayer");
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
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setUniqueId(tag_UUID, playerIn.getUniqueID());
		}
	}
}
