package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.helpers.SSPlayerHelper;

public class ItemSoulBinder extends ItemModSpecial {

	public ItemSoulBinder() {
		super();
		setUnlocalizedName("soulBinder");
	}
	
	@Override
	public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
		if(!p_77622_1_.hasTagCompound())
			p_77622_1_.setTagCompound(new NBTTagCompound());
		p_77622_1_.getTagCompound().setString("PlayerUUID", p_77622_3_.getUniqueID().toString());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
		if(p_77624_1_.hasTagCompound()) {
			if(SSPlayerHelper.getPlayerFromDimensions(p_77624_1_.getTagCompound().getString("PlayerUUID")) != null)
				p_77624_3_.add(SSPlayerHelper.getPlayerFromDimensions(p_77624_1_.getTagCompound().getString("PlayerUUID")).getGameProfile().getName());
			else
				p_77624_3_.add(StatCollector.translateToLocal("ss.misc.brokenPlayer"));
		} else
			p_77624_3_.add(StatCollector.translateToLocal("ss.misc.broken"));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if(!p_77659_2_.isRemote) {
			if(!p_77659_1_.hasTagCompound())
				p_77659_1_.setTagCompound(new NBTTagCompound());
			p_77659_1_.getTagCompound().setString("PlayerUUID", p_77659_3_.getUniqueID().toString());
		}
		return p_77659_1_;
	}
}
