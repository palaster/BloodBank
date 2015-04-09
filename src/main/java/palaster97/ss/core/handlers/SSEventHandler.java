package palaster97.ss.core.handlers;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityRitual;
import palaster97.ss.core.helpers.SSItemStackHelper;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemAtmanSword;
import palaster97.ss.items.SSItems;
import palaster97.ss.libs.LibMod;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;
import palaster97.ss.network.server.ChangeBlockMessage;
import palaster97.ss.rituals.Ritual;
import palaster97.ss.rituals.RitualActive;

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
	public void onPlayerPickUpExp(PlayerPickupXpEvent e) {
		if(!e.entityPlayer.worldObj.isRemote)
			if(e.entityPlayer.inventory.hasItem(SSItems.journal))
				for(int i = 0; i < e.entityPlayer.inventory.getSizeInventory(); i++)
					if(e.entityPlayer.inventory.getStackInSlot(i) != null && e.entityPlayer.inventory.getStackInSlot(i).getItem() == SSItems.journal)
						if(e.entityPlayer.inventory.getStackInSlot(i).hasTagCompound())
							if(e.entityPlayer.inventory.getStackInSlot(i).getTagCompound().getBoolean("Activated")) {
								e.entityPlayer.inventory.getStackInSlot(i).getTagCompound().setInteger("Level", e.entityPlayer.inventory.getStackInSlot(i).getTagCompound().getInteger("Level") + e.orb.getXpValue());
								e.orb.setDead();
								e.setCanceled(true);
							}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		if(!e.entityLiving.worldObj.isRemote) {
			if(e.source.getEntity() instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) e.source.getEntity();
				if(p.getCurrentEquippedItem() != null && p.getCurrentEquippedItem().getItem() instanceof ItemAtmanSword) {
					int temp = p.worldObj.rand.nextInt(8);
					if(temp == 0) {
						float temp1 = e.entityLiving.getMaxHealth();
						temp1 = temp1/10;
						if(temp1 <= 0)
							temp1 = 1;
						ItemStack stack = new ItemStack(SSItems.mobSouls);
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
				SoulNetworkExtendedPlayer props = SoulNetworkExtendedPlayer.get(p);
				if(props != null) {
					for(int i = 0; i < props.getActives().length; i++) {
						if(props.getActives()[i] != null) {
							BlockPos pos = props.getActives()[i].ritualPos;
							if(pos != null) {
								if(p.getDistanceSq(pos) <= 12) {
									TileEntityRitual ritual = (TileEntityRitual) p.worldObj.getTileEntity(pos);
									if(ritual != null && ritual.getIsActive())
										if(Ritual.rituals[ritual.getRitualID()] != null && Ritual.rituals[ritual.getRitualID()] instanceof RitualActive)
											ritual.setRitualLength(24);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerSleepInBed(PlayerSleepInBedEvent e) {
		if(!e.entityPlayer.worldObj.isRemote && e.result == EnumStatus.OK || !e.entityPlayer.worldObj.isRemote && e.result == null)
			if(e.entityPlayer.getCurrentEquippedItem() != null && e.entityPlayer.getCurrentEquippedItem().getItem() == Items.writable_book) {
				if(!e.entityPlayer.getCurrentEquippedItem().hasTagCompound() || !e.entityPlayer.getCurrentEquippedItem().getTagCompound().hasKey("pages", 9)) {
					ItemStack journal = new ItemStack(SSItems.journal);
					SSItems.journal.onCreated(journal, e.entityPlayer.worldObj, e.entityPlayer);
					e.entityPlayer.setCurrentItemOrArmor(0, journal);
				} else {
					NBTTagList nbttaglist = e.entityPlayer.getCurrentEquippedItem().getTagCompound().getTagList("pages", 8);
		            for(int i = 0; i < nbttaglist.tagCount(); ++i)
		                if(nbttaglist.getStringTagAt(i) == null || nbttaglist.getStringTagAt(i).length() > 32767) {
		                	ItemStack journal = new ItemStack(SSItems.journal);
							SSItems.journal.onCreated(journal, e.entityPlayer.worldObj, e.entityPlayer);
							e.entityPlayer.setCurrentItemOrArmor(0, journal);
		                }
				}
			}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayStreamSource(PlayStreamingSourceEvent e) {
		if(e.sound.getSoundLocation().equals(new ResourceLocation(LibMod.modid + ":records.unlight"))) {
			BlockPos jukePos = new BlockPos(e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF());
			IBlockState juke = Minecraft.getMinecraft().theWorld.getBlockState(jukePos);
			if(jukePos != null && juke != null && juke.getBlock() == Blocks.jukebox){
				BlockJukebox jukeBox = (BlockJukebox) juke.getBlock();
				if(jukeBox != null) {
					TileEntityJukebox teJuke = (TileEntityJukebox) Minecraft.getMinecraft().theWorld.getTileEntity(jukePos);
					if(teJuke != null) {
						ItemStack stack = SSItemStackHelper.getItemStackFromInventory(DimensionManager.getWorld(Minecraft.getMinecraft().theWorld.provider.getDimensionId()), jukePos, -1);
						SSItemStackHelper.setItemStackFromInventory(null, DimensionManager.getWorld(Minecraft.getMinecraft().theWorld.provider.getDimensionId()), jukePos, -1);
						if(stack != null && stack.getItem() == SSItems.recordUnlight)
							PacketHandler.sendToServer(new ChangeBlockMessage(jukePos, Blocks.obsidian));
					}
				}
			}
		}
	}
}
 