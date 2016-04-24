package palaster.bb.core.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.UndeadCapabilityProvider;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.knowledge.BBKnowledge;
import palaster.bb.items.*;
import palaster.bb.libs.LibMod;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.server.KeyClickMessage;
import palaster.bb.world.BBWorldSaveData;
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
			if(player != null && !player.hasCapability(UndeadCapabilityProvider.undeadCap, null))
				e.addCapability(new ResourceLocation(LibMod.modid, "IUndead"), new UndeadCapabilityProvider());
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) {
		if(e.isWasDeath()) {
			// Clone Player from death for Blood Bank.
			BBApi.setMaxBlood(e.getEntityPlayer(), BBApi.getMaxBlood(e.getOriginal()));
			BBApi.setCurrentBlood(e.getEntityPlayer(), BBApi.getCurrentBlood(e.getOriginal()));
			BBApi.linkEntity(e.getEntityPlayer(), BBApi.getLinked(e.getOriginal()));

			// Clone Player from death for Blood Bank.
			BBApi.setUndead(e.getEntityPlayer(), BBApi.isUndead(e.getOriginal()));
			BBApi.setSoul(e.getEntityPlayer(), BBApi.getSoul(e.getOriginal()));
			BBApi.setVigor(e.getEntityPlayer(), BBApi.getVigor(e.getOriginal()));
			BBApi.setAttunement(e.getEntityPlayer(), BBApi.getAttunement(e.getOriginal()));
			BBApi.setEndurance(e.getEntityPlayer(), BBApi.getEndurance(e.getOriginal()));
			BBApi.setVitality(e.getEntityPlayer(), BBApi.getVitality(e.getOriginal()));
			BBApi.setStrength(e.getEntityPlayer(), BBApi.getStrength(e.getOriginal()));
			BBApi.setDexterity(e.getEntityPlayer(), BBApi.getDexterity(e.getOriginal()));
			BBApi.setIntelligence(e.getEntityPlayer(), BBApi.getIntelligence(e.getOriginal()));
			BBApi.setFaith(e.getEntityPlayer(), BBApi.getFaith(e.getOriginal()));
			BBApi.setLuck(e.getEntityPlayer(), BBApi.getLuck(e.getOriginal()));

			if(BBApi.isUndead(e.getOriginal())) {
				BBWorldSaveData bbWorldSaveData = BBWorldSaveData.get(e.getOriginal().worldObj);
				if(bbWorldSaveData != null) {
					BlockPos closetBonfire = bbWorldSaveData.getNearestBonfireToPlayer(e.getOriginal(), e.getOriginal().getPosition());
					if(closetBonfire != null)
						e.getEntityPlayer().setPosition(closetBonfire.getX(), closetBonfire.getY() + .25D, closetBonfire.getZ());
				}
				e.getEntityPlayer().inventory.copyInventory(e.getOriginal().inventory);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent e) {
		if(e.getEntityPlayer() != null && BBApi.isUndead(e.getEntityPlayer())) {
			for(EntityItem entityItem : e.getDrops())
				if(entityItem != null && entityItem.getEntityItem() != null)
					e.getEntityPlayer().inventory.addItemStackToInventory(entityItem.getEntityItem());
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			for(Entity entity : e.getEntityLiving().worldObj.loadedEntityList)
				if(entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entity;
					if(BBApi.getLinked(player) != null && BBApi.getLinked(player).getUniqueID() == e.getEntityLiving().getUniqueID())
						BBApi.linkEntity(player, null);
				}
			if(e.getEntityLiving() instanceof EntityPlayer) {
				if(BBApi.isUndead((EntityPlayer) e.getEntityLiving())) {
					if(e.getSource().getEntity() instanceof EntityPlayer) {
						EntityPlayer killer = (EntityPlayer) e.getSource().getEntity();
						if(BBApi.isUndead(killer))
							BBApi.addSoul(killer, BBApi.getSoul((EntityPlayer) e.getEntityLiving()));
					}
					BBApi.setSoul((EntityPlayer) e.getEntityLiving(), 0);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			if(e.getEntityLiving() instanceof EntityPlayer) {
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
						BBApi.getLinked(p).attackEntityFrom(BloodBank.proxy.bbBlood, e.getAmount());
						e.setCanceled(true);
					}
				if(e.getSource().isMagicDamage() && p.inventory.hasItemStack(new ItemStack(BBItems.bbResources, 1, 2)))
					e.setCanceled(true);
			}
			if(e.getSource().getEntity() instanceof EntityPlayer && e.getSource().damageType.equals("player")) {
				if(BBApi.isUndead((EntityPlayer) e.getSource().getEntity()) && BBApi.getStrength((EntityPlayer) e.getSource().getEntity()) > 0) {
					int strengthLvl = BBApi.getStrength((EntityPlayer) e.getSource().getEntity());
					float damageBefore = e.getAmount();
					e.setCanceled(true);
					e.getEntityLiving().attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) e.getSource().getEntity()), damageBefore + (float) (strengthLvl * .25));
				}
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
		if(e.getItemStack() != null && e.getItemStack().hasTagCompound()) {
			if(e.getItemStack().getTagCompound().getBoolean("HasVampireSigil"))
				e.getToolTip().add(I18n.translateToLocal("bb.misc.vampireSigil"));
			if(BBItemStackHelper.getCountDown(e.getItemStack()))
				e.getToolTip().add(I18n.translateToFallback("bb.misc.countDown"));
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if(!e.player.worldObj.isRemote)
			if(e.phase == TickEvent.Phase.START) {
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
				for(int i = 0; i < e.player.inventory.getSizeInventory(); i++)
					if(e.player.inventory.getStackInSlot(i) != null && e.player.inventory.getStackInSlot(i).hasTagCompound())
						if(BBItemStackHelper.getCountDown(e.player.inventory.getStackInSlot(i))) {
							if(e.player.inventory.getStackInSlot(0).getItemDamage() < e.player.inventory.getStackInSlot(0).getMaxDamage())
								e.player.inventory.getStackInSlot(0).damageItem(1, e.player);
							else
								e.player.inventory.setInventorySlotContents(i, null);
						}
				for(PotionEffect potionEffect : e.player.getActivePotionEffects())
					if(potionEffect != null)
						if(e.player.inventory.hasItemStack(new ItemStack(BBItems.bbResources, 1, 2)))
							e.player.removePotionEffect(potionEffect.getPotion());
			}
	}

	@SubscribeEvent
	public void onItemToss(ItemTossEvent e) {
		if(!e.getPlayer().worldObj.isRemote)
			if(e.getEntityItem().getEntityItem().hasTagCompound() && BBItemStackHelper.getCountDown(e.getEntityItem().getEntityItem()))
				e.setCanceled(true);
			if(e.getEntityItem().getEntityItem() != null && e.getEntityItem().getEntityItem().getItem() instanceof BBArmor) {
				if(e.getEntityItem().getEntityItem().getItem() == BBItems.boundHelmet || e.getEntityItem().getEntityItem().getItem() == BBItems.boundChestplate || e.getEntityItem().getEntityItem().getItem() == BBItems.boundLeggings || e.getEntityItem().getEntityItem().getItem() == BBItems.boundBoots)
					if(BBItemStackHelper.getItemStackFromItemStack(e.getEntityItem().getEntityItem()) != null)
						e.getPlayer().worldObj.spawnEntityInWorld(new EntityItem(e.getPlayer().worldObj, e.getPlayer().posX, e.getPlayer().posY, e.getPlayer().posZ, BBItemStackHelper.getItemStackFromItemStack(e.getEntityItem().getEntityItem())));
				e.setCanceled(true);
			}
	}

	@SubscribeEvent
	public void onKeyboardInput(InputEvent.KeyInputEvent e) {
		if(Minecraft.getMinecraft().inGameHasFocus)
			if(Keyboard.isKeyDown(Keyboard.KEY_U))
				PacketHandler.sendToServer(new KeyClickMessage());
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