package palaster.bb.core.handlers;

import java.io.File;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.recipes.ShapedBloodRecipes;
import palaster.bb.core.proxy.ClientProxy;
import palaster.bb.entities.EntityItztiliTablet;
import palaster.bb.entities.careers.CareerApothecary;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.careers.CareerUndead;
import palaster.bb.entities.effects.BBPotions;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemBBResources;
import palaster.bb.items.ItemBloodBottle;
import palaster.bb.items.ItemBoundBloodBottle;
import palaster.bb.items.ItemBoundPlayer;
import palaster.bb.items.ItemModStaff;
import palaster.bb.libs.LibMod;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.server.KeyClickMessage;
import palaster.bb.world.BBWorldSaveData;
import palaster.bb.world.WorldEventListener;

public class BBEventHandler {

	// Config Values
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
		if(e.getEntity() instanceof EntityPlayer)
			if((EntityPlayer) e.getEntity() != null && !((EntityPlayer) e.getEntity()).hasCapability(RPGCapabilityProvider.rpgCap, null))
				e.addCapability(new ResourceLocation(LibMod.modid, "IRPG"), new RPGCapabilityProvider());
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) {
		if(!e.getEntityPlayer().worldObj.isRemote) {
			final IRPG rpgOG = RPGCapabilityProvider.get(e.getOriginal());
			final IRPG rpgNew = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpgOG != null && rpgNew != null) {
				if(rpgOG.getCareer() != null)
					rpgNew.setCareer(rpgOG.getCareer());
				rpgNew.setConstitution(rpgOG.getConstitution());
				BBApi.calculateConstitutionBoost(e.getEntityPlayer());
				rpgNew.setStrength(rpgOG.getStrength());
				BBApi.calculateStrengthBoost(e.getEntityPlayer());
				rpgNew.setDefense(rpgOG.getDefense());
				rpgNew.setDexterity(rpgOG.getDexterity());
				BBApi.calculateDexterityBoost(e.getEntityPlayer());
				if(e.isWasDeath()) {
					if(rpgOG.getCareer() != null && rpgOG.getCareer() instanceof CareerUndead) {
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
		}
	}

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent e) {
		if(e.getEntityPlayer() != null) {
			final IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpg != null)
				if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead) {
					for(EntityItem entityItem : e.getDrops())
						if(entityItem != null && entityItem.getEntityItem() != null)
							e.getEntityPlayer().inventory.addItemStackToInventory(entityItem.getEntityItem());
					e.setCanceled(true);
				} 
			
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			for(Entity entity : e.getEntityLiving().worldObj.loadedEntityList)
				if(entity instanceof EntityPlayer) {
					final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) entity);
					if(rpg != null)
						if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer)
							if(((CareerBloodSorcerer) rpg.getCareer()).isLinked() && e.getEntityLiving().getUniqueID().equals(((CareerBloodSorcerer) rpg.getCareer()).getLinked().getUniqueID()))
								((CareerBloodSorcerer) rpg.getCareer()).linkEntity(null);
				}
			if(e.getEntityLiving() instanceof EntityPlayer) {
				final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getEntityLiving());
				if(rpg != null) {
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead) {
						if(e.getSource().getEntity() instanceof EntityPlayer) {
							final IRPG rpgAttacker = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getEntity());
							if(rpgAttacker != null)
								if(rpgAttacker.getCareer() != null && rpgAttacker.getCareer() instanceof CareerUndead)
									((CareerUndead) rpgAttacker.getCareer()).addSoul(((CareerUndead) rpg.getCareer()).getSoul());
						} else if(((CareerUndead) rpg.getCareer()).getSoul() > 0) {
							ItemStack souls = new ItemStack(BBItems.bbResources, 1, 6);
							if(!souls.hasTagCompound())
								souls.setTagCompound(new NBTTagCompound());
							souls.getTagCompound().setInteger(ItemBBResources.tag_soulAmount, ((CareerUndead) rpg.getCareer()).getSoul());
							e.getEntityLiving().worldObj.spawnEntityInWorld(new EntityItem(e.getEntityLiving().worldObj, e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, souls));
						}
						((CareerUndead) rpg.getCareer()).setSoul(0);
					}
				}
			}
			if(e.getSource().getEntity() instanceof EntityPlayer) {
				final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getEntity());
				if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead)
					((CareerUndead) rpg.getCareer()).addSoul((int) e.getEntityLiving().getMaxHealth());
			}
			if(e.getEntityLiving() instanceof EntityLiving) {
				NBTTagCompound nbt = new NBTTagCompound();
				((EntityLiving) e.getEntityLiving()).writeToNBTAtomically(nbt);
				BBWorldSaveData.get(e.getEntityLiving().worldObj).addDeadEntity(nbt);
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			if(e.getEntityLiving() instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) e.getEntityLiving();
				if(e.getSource().getEntity() != null) {
					final IRPG rpg = RPGCapabilityProvider.get(p);
					if(rpg != null)
						if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer)
							if(((CareerBloodSorcerer) rpg.getCareer()).isLinked()) {
								((CareerBloodSorcerer) rpg.getCareer()).getLinked().attackEntityFrom(BloodBank.proxy.bbBlood, e.getAmount());
								e.setCanceled(true);
							}
					if(p.getActivePotionEffect(BBPotions.sandBody) != null)
						if(e.getSource().getEntity() instanceof EntityPlayer) {
							((EntityPlayer) e.getSource().getEntity()).inventory.addItemStackToInventory(new ItemStack(BBItems.bbResources, 1, 5));
							e.setCanceled(true);
						} else if(e.getSource().getEntity() instanceof EntityLiving) {
							((EntityLiving) e.getSource().getEntity()).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 2, false, true));
							e.setCanceled(true);
						}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e) {
		if(e.getEntityLiving() instanceof EntityItztiliTablet)
			if(e.getSource() != DamageSource.outOfWorld && e.getSource() != DamageSource.inWall)
				e.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onBedCheck(SleepingLocationCheckEvent e) {
		if(!e.getEntityPlayer().worldObj.isRemote)
			if(e.getEntityPlayer().getActivePotionEffect(BBPotions.curseUnblinkingEye) != null)
				e.setResult(Event.Result.DENY);
	}
	
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent e) {
		if(!e.player.worldObj.isRemote) {
			if(CraftingManager.getInstance().getRecipeList() != null)
				for(IRecipe recipe : CraftingManager.getInstance().getRecipeList())
					if(recipe != null && recipe instanceof ShapedBloodRecipes)
						if(recipe.getRecipeOutput() != null && recipe.getRecipeOutput().getItem() == e.crafting.getItem() && recipe.getRecipeOutput().getItemDamage() == e.crafting.getItemDamage()) {
							for(int i = 0; i < e.player.inventory.getSizeInventory(); i++)
								if(e.player.inventory.getStackInSlot(i) != null)
									if(e.player.inventory.getStackInSlot(i).getItem() instanceof ItemBloodBottle)
										if((e.player.inventory.getStackInSlot(i).getMaxDamage() - e.player.inventory.getStackInSlot(i).getItemDamage()) >= ((ShapedBloodRecipes) recipe).recipeBloodCost) {
											e.player.inventory.getStackInSlot(i).damageItem(((ShapedBloodRecipes) recipe).recipeBloodCost, e.player);
											return;
										}
							final IRPG rpg = RPGCapabilityProvider.get(e.player);
							if(rpg != null) {
								if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
									int temp = ((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(((ShapedBloodRecipes) recipe).recipeBloodCost); 
									if(temp > 0) {
										e.player.attackEntityFrom(BloodBank.proxy.bbBlood, temp / 100);
										return;
									}
								}
							} else {
								e.player.attackEntityFrom(BloodBank.proxy.bbBlood, (((ShapedBloodRecipes) recipe).recipeBloodCost / 100));
								return;
							}
						}
			if(e.crafting != null && e.crafting.getItem() == BBItems.boundBloodBottle)
				for(int i = 0; i < e.craftMatrix.getSizeInventory(); i++)
					if(e.craftMatrix.getStackInSlot(i) != null && e.craftMatrix.getStackInSlot(i).getItem() == BBItems.boundPlayer)
						if(e.craftMatrix.getStackInSlot(i).hasTagCompound()) {
							if(!e.crafting.hasTagCompound())
								e.crafting.setTagCompound(new NBTTagCompound());
							e.crafting.getTagCompound().setUniqueId(ItemBoundBloodBottle.tag_UUID, e.craftMatrix.getStackInSlot(i).getTagCompound().getUniqueId(ItemBoundPlayer.tag_UUID));
						}
		}
	}
	
	@SubscribeEvent
	public void onHarvestDrops(HarvestDropsEvent e) {
		if(!e.getWorld().isRemote)
			if(e.getState() != null && e.getState().getBlock() != null && e.getState().getBlock() instanceof BlockCrops)
				if(e.getHarvester() != null) {
					final IRPG rpg = RPGCapabilityProvider.get(e.getHarvester());
					if(rpg != null)
						if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerApothecary)
							for(ItemStack stack : e.getDrops())
								if(stack != null)
									stack.stackSize += 1;
				}
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		if(!e.getWorld().isRemote)
			e.getWorld().addEventListener(new WorldEventListener());
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			if(e.getEntityLiving().getActivePotionEffect(BBPotions.timedFlame) != null && e.getEntityLiving().getActivePotionEffect(BBPotions.timedFlame).getDuration() == 1)
				e.getEntityLiving().setFire(300);
			else if(e.getEntityLiving().getActivePotionEffect(BBPotions.instantDeath) != null && e.getEntityLiving().getActivePotionEffect(BBPotions.instantDeath).getDuration() == 1)
				e.getEntityLiving().attackEntityFrom(DamageSource.magic, Float.MAX_VALUE);
			else if(e.getEntityLiving().getActivePotionEffect(BBPotions.bloodHive) != null && e.getEntityLiving().getActivePotionEffect(BBPotions.bloodHive).getDuration() == 1) {
				EntityTNTPrimed tnt = new EntityTNTPrimed(e.getEntityLiving().worldObj, e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, e.getEntityLiving());
				e.getEntityLiving().worldObj.spawnEntityInWorld(tnt);
			} else if(e.getEntityLiving().getActivePotionEffect(BBPotions.darkWings) != null && e.getEntityLiving().getActivePotionEffect(BBPotions.darkWings).getDuration() == 1) {
				if(e.getEntityLiving() instanceof EntityPlayer)
					if(!((EntityPlayer) e.getEntityLiving()).isSpectator() && !((EntityPlayer) e.getEntityLiving()).capabilities.isCreativeMode) {
						((EntityPlayer) e.getEntityLiving()).capabilities.allowFlying = false;
						((EntityPlayer) e.getEntityLiving()).capabilities.isFlying = false;
						((EntityPlayer) e.getEntityLiving()).capabilities.disableDamage = false;
						((EntityPlayer) e.getEntityLiving()).sendPlayerAbilities();
					}
			}
			if(e.getEntityLiving().getActivePotionEffect(BBPotions.darkWings) != null && e.getEntityLiving().getActivePotionEffect(BBPotions.darkWings).getDuration() > 1)
				if(e.getEntityLiving() instanceof EntityPlayer && !((EntityPlayer) e.getEntityLiving()).capabilities.allowFlying) {
					((EntityPlayer) e.getEntityLiving()).capabilities.allowFlying = true;
					((EntityPlayer) e.getEntityLiving()).sendPlayerAbilities();
				}
		}
	}
	
	@SubscribeEvent
	public void onPlayerSendChat(ServerChatEvent e) {
		if(!e.getPlayer().worldObj.isRemote)
			if(e.getPlayer().getActivePotionEffect(BBPotions.curseBlackTongue) != null)
				e.setComponent(new TextComponentString("<" + e.getUsername() + "> " + I18n.format("bb.gimp." + e.getPlayer().worldObj.rand.nextInt(4))));
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent e) {
		if(e.side.isServer())
			if((e.world.getTotalWorldTime() % 84000) == 0)
				BBWorldSaveData.get(e.world).clearDeadEntities(e.world); // 7 Minecraft Days
	}

	@SubscribeEvent
	public void onKeyboardInput(InputEvent.KeyInputEvent e) {
		if(Minecraft.getMinecraft().inGameHasFocus)
			if(Keyboard.isKeyDown(ClientProxy.staffChange.getKeyCode()))
				PacketHandler.sendToServer(new KeyClickMessage());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post e) {
		if(Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().inGameHasFocus) {
			if(e.getType() == ElementType.TEXT && Minecraft.getMinecraft().fontRendererObj != null) {
				EntityPlayer p = Minecraft.getMinecraft().thePlayer;
				if(p != null) {
					if(p.getHeldItemOffhand() != null && p.getHeldItemOffhand().getItem() instanceof ItemModStaff && p.getHeldItemOffhand().hasTagCompound()) {
						ItemStack staff = Minecraft.getMinecraft().thePlayer.getHeldItemOffhand();
						String power = I18n.format(((ItemModStaff) staff.getItem()).powers[ItemModStaff.getActivePower(staff)]);
						Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format("bb.staff.active") + ": " + power, 2, 2, 0);
						ClientProxy.isItemInOffHandRenderingOverlay = true;
					} else if(p.getHeldItemOffhand() == null)
						ClientProxy.isItemInOffHandRenderingOverlay = false;
					if(!ClientProxy.isItemInOffHandRenderingOverlay && p.getHeldItemMainhand() != null && p.getHeldItemMainhand().getItem() instanceof ItemModStaff && p.getHeldItemMainhand().hasTagCompound()) {
						ItemStack staff = Minecraft.getMinecraft().thePlayer.getHeldItemMainhand();
						String power = I18n.format(((ItemModStaff) staff.getItem()).powers[ItemModStaff.getActivePower(staff)]);
						Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format("bb.staff.active") + ": " + power, 2, 2, 0);					
					}
				}
			}	
		}
	}
}