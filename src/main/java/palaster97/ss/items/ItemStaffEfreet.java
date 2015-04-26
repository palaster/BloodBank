package palaster97.ss.items;

import java.util.List;

import palaster97.ss.core.helpers.SSPlayerHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
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
		if(player.inventory.hasItem(SSItems.journal))
			if(SSPlayerHelper.getJournalAmount(player) >= 5)
				if(SSPlayerHelper.reduceJournalAmount(player, 5)) {
					for(EntityLivingBase entity : (List<EntityLivingBase>) player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - 8, player.posY - 3, player.posZ - 8, player.posX + 8, player.posY + 8, player.posZ + 8)))
						if(entity != player)
							if(!player.worldObj.isRemote)
								entity.setFire(1);
					if(player.worldObj.isRemote)
						for(int i = -2; i <= 2; ++i)
							for(int j = -2; j <= 2; ++j)
				            	for(int k = 0; k <= 1; ++k)
				            		player.worldObj.spawnParticle(EnumParticleTypes.FLAME, player.posX + .5D, player.posY + 1D, player.posZ + .5D, (double)((float)i + player.worldObj.rand.nextFloat()) - 0.5D, (double)((float)k - player.worldObj.rand.nextFloat() - 1.0F), (double)((float)j + player.worldObj.rand.nextFloat()) - 0.5D, new int[0]);
				}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
}