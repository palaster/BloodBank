package palaster97.ss.core.handlers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemAtmanSword;
import palaster97.ss.items.SSItems;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;

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
			if(e.source.getEntity() instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) e.source.getEntity();
				if(p.getCurrentEquippedItem() != null && p.getCurrentEquippedItem().getItem() instanceof ItemAtmanSword) {
					int temp = p.worldObj.rand.nextInt(8);
					if(temp == 0) {
						float temp1 = e.entityLiving.getMaxHealth();
						temp1 = temp1/10;
						if(temp1 <= 0)
							temp1 = 1;
						ItemStack stack = new ItemStack(SSItems.souls);
						stack.setTagCompound(new NBTTagCompound());
						stack.getTagCompound().setInteger("Level", (int) temp1);
						if(e.entityLiving instanceof EntityPlayer) {
							stack.getTagCompound().setBoolean("IsPlayer", true);
							stack.getTagCompound().setString("PlayerUUID", ((EntityPlayer) e.entityLiving).getGameProfile().getName());
						}
						EntityItem item = new EntityItem(p.worldObj, p.posX, p.posY, p.posZ, stack);
						p.worldObj.spawnEntityInWorld(item);
					}
				}
			}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.entityLiving.worldObj.isRemote && e.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.source.getEntity();
			if(p != null)
				if(p.getHeldItem() != null && p.getHeldItem().getItem() == SSItems.staffCacklingWrath) {
					if(p.getHealth() + (e.ammount / 2) >= 20f)
						p.setHealth(20f);
					else
						p.setHealth(p.getHealth() + (e.ammount / 2));
					p.getHeldItem().damageItem(1, e.entityLiving);
				}
		}
	}
	
	@SubscribeEvent
	public void onPlayerSleepInBed(PlayerSleepInBedEvent e) {
		if(!e.entityPlayer.worldObj.isRemote && e.result == EnumStatus.OK || !e.entityPlayer.worldObj.isRemote && e.result == null)
			if(SoulNetworkExtendedPlayer.get(e.entityPlayer) != null && !SoulNetworkExtendedPlayer.get(e.entityPlayer).getRuneCharge())
				SoulNetworkExtendedPlayer.get(e.entityPlayer).setRuneCharge(true);	
	}
}
 