package palaster97.ss.items;

import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDebug extends ItemModSpecial {

	public ItemDebug() {
		super();
		setUnlocalizedName("debug");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote)
			if(SoulNetworkExtendedPlayer.get(playerIn) != null)
				if(SoulNetworkExtendedPlayer.get(playerIn).getBurningChild() != null)
					SoulNetworkExtendedPlayer.get(playerIn).setBurningChild(null);
		return itemStackIn;
	}
}
