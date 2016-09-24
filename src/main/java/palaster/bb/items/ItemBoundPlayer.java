package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBoundPlayer extends ItemModSpecial {
	
	public static final String TAG_UUID_PLAYER = "BoundPlayerUUID";

	public ItemBoundPlayer(String unlocalizedName) { super(unlocalizedName); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			if(playerIn.worldObj.getPlayerEntityByUUID(stack.getTagCompound().getUniqueId(TAG_UUID_PLAYER)) != null)
				tooltip.add(playerIn.worldObj.getPlayerEntityByUUID(stack.getTagCompound().getUniqueId(TAG_UUID_PLAYER)).getName());
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setUniqueId(TAG_UUID_PLAYER, playerIn.getUniqueID());
		}
	}
}
