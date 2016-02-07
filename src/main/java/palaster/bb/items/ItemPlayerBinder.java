package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.helpers.BBPlayerHelper;

public class ItemPlayerBinder extends ItemModSpecial {

	public ItemPlayerBinder() {
		super();
		setUnlocalizedName("playerBinder");
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if(stack.hasTagCompound()) {
			if(BBPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")) != null)
				tooltip.add(BBPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")).getGameProfile().getName());
			else
				tooltip.add(StatCollector.translateToLocal("bb.misc.brokenPlayer"));
		} else
			tooltip.add(StatCollector.translateToLocal("bb.misc.broken"));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(!itemStackIn.hasTagCompound())
				itemStackIn.setTagCompound(new NBTTagCompound());
			itemStackIn.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
		}
		return itemStackIn;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
		if(!playerIn.worldObj.isRemote && target instanceof EntityPlayer)
			if(playerIn.isSneaking()) {
				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setString("PlayerUUID", target.getUniqueID().toString());
			}
		return false;
	}
}
