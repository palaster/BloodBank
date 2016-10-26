package palaster.bb.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.BloodBank;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.core.helpers.NBTHelper;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.libpal.items.ItemModSpecial;

public class ItemVampireSigil extends ItemModSpecial {
	
	public static final String TAG_BOOLEAN_VAMPIRE_SIGIL = "HasVampireSigil";

	public ItemVampireSigil(ResourceLocation rl) {
		super(rl);
		MinecraftForge.EVENT_BUS.register(this);
	}


    @SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getSource().getEntity() instanceof EntityPlayer)
				if(((EntityPlayer )e.getSource().getSourceOfDamage()).getHeldItemMainhand() != null && ((EntityPlayer)e.getSource().getSourceOfDamage()).getHeldItemMainhand().getItem() instanceof ItemSword)
					if(((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND) != null && ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND).getItem() == this) {
						final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getSourceOfDamage());
						if(rpg != null)
							if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer)
								((CareerBloodSorcerer) rpg.getCareer()).addBlood((int) e.getAmount() * 50);
					}
	}

    @SubscribeEvent
	public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem e) {
		if(!e.getWorld().isRemote && e.getSide().isServer())
			if(e.getHand() == EnumHand.MAIN_HAND)
				if(e.getEntityPlayer().getHeldItemMainhand() != null && e.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemSword)
					if(e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND) != null && e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == this) {
						final IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
						if(rpg != null)
							if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
								((CareerBloodSorcerer) rpg.getCareer()).addBlood(100);
								e.getEntityPlayer().attackEntityFrom(BloodBank.proxy.bbBlood, 1f);
							}
					}
	}
    
    @SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e) {
		if(e.getLeft() != null && e.getRight() != null) {
			ItemStack copy = e.getLeft().copy();
			if(e.getLeft().getItem().isRepairable() && !(NBTHelper.getBooleanFromItemStack(e.getLeft(), TAG_BOOLEAN_VAMPIRE_SIGIL)))
				if(e.getRight().getItem() == this) {
					e.setMaterialCost(1);
					e.setCost(1);
					e.setOutput(NBTHelper.setBooleanToItemStack(copy, TAG_BOOLEAN_VAMPIRE_SIGIL, true));
				}
		}
	}
    
    @SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null)
			if(NBTHelper.getBooleanFromItemStack(e.getItemStack(), TAG_BOOLEAN_VAMPIRE_SIGIL))
				e.getToolTip().add(I18n.format("bb.misc.vampireSigil"));
	}
}
