package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemStaffEfreet extends ItemModSpecial {
	
	public ItemStaffEfreet() {
		super();
		setUnlocalizedName("staffEfreet");
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) { return 1; }
	
	@SuppressWarnings("unchecked")
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		if(!player.worldObj.isRemote) {
			for(EntityLivingBase entity : (List<EntityLivingBase>) player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - 8, player.posY - 3, player.posZ - 8, player.posX + 8, player.posY + 8, player.posZ + 8)))
				if(entity != player)
					entity.setFire(1);
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote)
			playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
}
