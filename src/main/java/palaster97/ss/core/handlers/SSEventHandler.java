package palaster97.ss.core.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemTrident;
import palaster97.ss.items.SSItems;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;
import palaster97.ss.world.SSWorldManager;

public class SSEventHandler {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e) {
		if(e.entity instanceof EntityPlayer)
			if(SoulNetworkExtendedPlayer.get((EntityPlayer) e.entity) == null)
				SoulNetworkExtendedPlayer.register((EntityPlayer) e.entity);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e) {
		if(e.entity instanceof EntityPlayerMP)
			PacketHandler.sendTo(new SyncPlayerPropsMessage((EntityPlayer) e.entity), (EntityPlayerMP) e.entity);		
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) { SoulNetworkExtendedPlayer.get(e.entityPlayer).copy(SoulNetworkExtendedPlayer.get(e.original)); }

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		if(!e.entityLiving.worldObj.isRemote)
			if(e.entityLiving instanceof EntityPlayer && ((EntityPlayer)e.entityLiving).getUniqueID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9")) {
				if(((EntityPlayer)e.entityLiving).getBedLocation(0) != null)
					((EntityPlayer)e.entityLiving).setPosition(((EntityPlayer)e.entityLiving).getBedLocation().getX(), ((EntityPlayer)e.entityLiving).getBedLocation().getY() + 1, ((EntityPlayer)e.entityLiving).getBedLocation().getZ());
				else
					((EntityPlayer)e.entityLiving).setPosition(((EntityPlayer)e.entityLiving).worldObj.getSpawnPoint().getX(), ((EntityPlayer)e.entityLiving).worldObj.getSpawnPoint().getY() + .25f, ((EntityPlayer)e.entityLiving).worldObj.getSpawnPoint().getZ());
				e.setCanceled(true);
			}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.entityLiving.worldObj.isRemote && e.entityLiving instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.entityLiving;
			if(e.source == DamageSource.drown)
				if(p.inventory.hasItem(SSItems.trident))
					for(int i = 0; i < 9; i++)
						if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() instanceof ItemTrident) {
							e.setCanceled(true);
							p.inventory.getStackInSlot(i).damageItem(1, p);
						}
		}
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		if(!e.world.isRemote)
			e.world.addWorldAccess(new SSWorldManager());
	}
	
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.itemStack != null && e.itemStack.hasTagCompound() && e.itemStack.getTagCompound().getBoolean("HasTapeHeart")) {
			String temp = e.toolTip.get(e.toolTip.size() - 1);
			e.toolTip.set(e.toolTip.size() - 1, StatCollector.translateToLocal("ss.misc.tapeHeart"));
			e.toolTip.add(temp);
		}
	}
}