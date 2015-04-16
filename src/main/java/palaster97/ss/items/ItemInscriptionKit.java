package palaster97.ss.items;

import palaster97.ss.ScreamingSouls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemInscriptionKit extends ItemMod {

	public ItemInscriptionKit() {
		super();
		setUnlocalizedName("inscriptionKit");
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) { return 1; }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote)
			playerIn.openGui(ScreamingSouls.instance, 6, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		return itemStackIn;
	}
}
