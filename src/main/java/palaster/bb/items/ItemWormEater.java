package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import palaster.libpal.items.ItemModSpecial;

public class ItemWormEater extends ItemModSpecial {

	public ItemWormEater(ResourceLocation rl) {
		super(rl);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getEntityLiving() instanceof EntityPlayer)
				if(e.getSource().isMagicDamage() && ((EntityPlayer) e.getEntityLiving()).inventory.hasItemStack(new ItemStack(this)))
					e.setCanceled(true);
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if(!e.player.worldObj.isRemote)
			if(e.phase == TickEvent.Phase.START)
				if(e.player.inventory.hasItemStack(new ItemStack(this)))
					e.player.clearActivePotions();
	}
}
