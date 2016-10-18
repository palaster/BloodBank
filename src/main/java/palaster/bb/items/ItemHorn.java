package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import palaster.libpal.items.ItemModSpecial;

public class ItemHorn extends ItemModSpecial{

	public ItemHorn(ResourceLocation rl) { super(rl); }
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		if(!player.worldObj.isRemote) {
			List<EntityPlayer> players = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(player.posX - 6, player.posY - 2, player.posZ - 6, player.posX + 6, player.posY + 2, player.posZ + 6));
			if(players != null && !players.isEmpty())
				for(EntityPlayer p : players)
					if(!p.getUniqueID().equals(player.getUniqueID()))
						p.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, 2, false, true));					
			player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 1, 1, false, true));
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) { return 1200; }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			playerIn.setActiveHand(hand);
			return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
