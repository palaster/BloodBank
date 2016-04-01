package palaster.bb.core.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.entities.knowledge.BBKnowledge;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemBookBlood;
import palaster.bb.items.ItemModStaff;
import palaster.bb.items.ItemTrident;
import palaster.bb.libs.LibMod;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.server.MiddleClickMessage;
import palaster.bb.world.WorldEventListener;

import java.io.File;

public class BBEventHandler {

	public static Configuration config;

	public static void init(File configFile) {
		if(config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equalsIgnoreCase(LibMod.modid))
			loadConfiguration();
	}

	private static void loadConfiguration() {
		if(config.hasChanged())
			config.save();
	}

	@SubscribeEvent
	public void attachEntityCapability(AttachCapabilitiesEvent.Entity e) {
		if(e.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.getEntity();
			if(player != null && !player.hasCapability(BloodBankCapabilityProvider.bloodBankCap, null))
				e.addCapability(new ResourceLocation(LibMod.modid, "IBloodBank"), new BloodBankCapabilityProvider());
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) {
		if(e.isWasDeath()) {
			BBApi.setMaxBlood(e.getEntityPlayer(), BBApi.getMaxBlood(e.getOriginal()));
			BBApi.setCurrentBlood(e.getEntityPlayer(), BBApi.getCurrentBlood(e.getOriginal()));
			BBApi.linkEntity(e.getEntityPlayer(), BBApi.getLinked(e.getOriginal()));
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			for(Entity entity : e.getEntityLiving().worldObj.loadedEntityList)
				if(entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entity;
					if(BBApi.getLinked(player) != null && BBApi.getLinked(player).getUniqueID() == e.getEntityLiving().getUniqueID())
						BBApi.linkEntity(player, null);
				}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote && e.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.getEntityLiving();
			if(e.getSource() == DamageSource.drown)
				if(p.inventory.hasItemStack(new ItemStack(BBItems.trident)))
					for(int i = 0; i < 9; i++)
						if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() instanceof ItemTrident) {
							e.setCanceled(true);
							p.inventory.getStackInSlot(i).damageItem(1, p);
						}
			if(e.getSource().getEntity() != null)
				if(BBApi.getLinked(p) != null) {
					EntityLiving link = BBApi.getLinked(p);
					link.attackEntityFrom(BloodBank.proxy.bbBlood, e.getAmount());
					e.setCanceled(true);
				}
		}
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		if(!e.getWorld().isRemote)
			e.getWorld().addEventListener(new WorldEventListener());
	}
	
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null && e.getItemStack().hasTagCompound() && e.getItemStack().getTagCompound().getBoolean("HasTapeHeart")) {
			String temp = e.getToolTip().get(e.getToolTip().size() - 1);
			e.getToolTip().set(e.getToolTip().size() - 1, I18n.translateToLocal("bb.misc.tapeHeart"));
			e.getToolTip().add(temp);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if(!e.player.worldObj.isRemote)
			if(e.phase == TickEvent.Phase.START)
				for(int i = 0; i < e.player.inventory.armorInventory.length; i++)
					if(e.player.inventory.armorInventory[i] != null) {
						if(e.player.inventory.armorInventory[i].getItem() == BBItems.boundHelmet)
							e.player.inventory.armorInventory[i].getItem().onUpdate(e.player.inventory.armorInventory[i], e.player.worldObj, e.player, 103, false);
						if(e.player.inventory.armorInventory[i].getItem() == BBItems.boundChestplate)
							e.player.inventory.armorInventory[i].getItem().onUpdate(e.player.inventory.armorInventory[i], e.player.worldObj, e.player, 102, false);
						if(e.player.inventory.armorInventory[i].getItem() == BBItems.boundLeggings)
							e.player.inventory.armorInventory[i].getItem().onUpdate(e.player.inventory.armorInventory[i], e.player.worldObj, e.player, 101, false);
						if(e.player.inventory.armorInventory[i].getItem() == BBItems.boundBoots)
							e.player.inventory.armorInventory[i].getItem().onUpdate(e.player.inventory.armorInventory[i], e.player.worldObj, e.player, 100, false);
					}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouseInput(InputEvent.MouseInputEvent e) {
		if(Minecraft.getMinecraft().inGameHasFocus)
			if(Mouse.isButtonDown(2))
				PacketHandler.sendToServer(new MiddleClickMessage());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority= EventPriority.NORMAL)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		if(Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().inGameHasFocus) {
			if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
				if(Minecraft.getMinecraft().fontRendererObj != null && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getHeldItemMainhand() != null) {
					if(Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().getItem() instanceof ItemModStaff && Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().hasTagCompound()) {
						ItemStack staff = Minecraft.getMinecraft().thePlayer.getHeldItemMainhand();
						String power = I18n.translateToLocal(((ItemModStaff) staff.getItem()).powers[ItemModStaff.getActivePower(staff)]);
						Minecraft.getMinecraft().fontRendererObj.drawString(I18n.translateToLocal("bb.staff.active") + ": " + power, 2, 2, 0);
					} else if(Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().getItem() instanceof ItemBookBlood && Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().hasTagCompound()) {
						ItemStack book = Minecraft.getMinecraft().thePlayer.getHeldItemMainhand();
						String spell = I18n.translateToLocal("bb.knowledgePiece") + ": " + I18n.translateToLocal(BBKnowledge.getKnowledgePiece(book.getTagCompound().getInteger("Knowledge Piece")).getName());
						Minecraft.getMinecraft().fontRendererObj.drawString(I18n.translateToLocal("bb.kp.active") + ": " + spell, 2, 2, 0x8A0707);
					}
				}
		}
	}
}