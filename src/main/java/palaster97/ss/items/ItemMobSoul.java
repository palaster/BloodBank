package palaster97.ss.items;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMobSoul extends ItemModSpecial {
	
	public ItemMobSoul() {
		super();
		setUnlocalizedName("mobSouls");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
		if(p_77624_1_.hasTagCompound()) {
			p_77624_3_.add(StatCollector.translateToLocal("ss.misc.level") + ": " + p_77624_1_.getTagCompound().getInteger("Level"));
			if(p_77624_1_.getTagCompound().getBoolean("IsPlayer")) {
				UUID uuid = UUID.fromString(p_77624_1_.getTagCompound().getString("PlayerUUID"));
				if(p_77624_2_.worldObj.getPlayerEntityByUUID(uuid) != null)
					p_77624_3_.add(StatCollector.translateToLocal("ss.misc.player") + ": " + p_77624_2_.worldObj.getPlayerEntityByUUID(uuid).getGameProfile().getName());
			}
		} else
			p_77624_3_.add(StatCollector.translateToLocal("ss.misc.broken"));
	}
}
