package palaster.bb.core.handlers;

import java.io.File;
import java.util.UUID;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import palaster.bb.BloodBank;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.entities.TameableMonsterCapability.TameableMonsterCapabilityProvider;
import palaster.bb.api.recipes.ShapedBloodRecipes;
import palaster.bb.core.helpers.NBTHelper;
import palaster.bb.core.proxy.ClientProxy;
import palaster.bb.core.proxy.CommonProxy;
import palaster.bb.entities.EntityItztiliTablet;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.careers.CareerGod;
import palaster.bb.entities.careers.CareerUnkindled;
import palaster.bb.entities.effects.BBPotions;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemBBResources;
import palaster.bb.items.ItemBloodBottle;
import palaster.bb.items.ItemBoundBloodBottle;
import palaster.bb.items.ItemBoundPlayer;
import palaster.bb.libs.LibMod;
import palaster.bb.network.server.KeyClickMessage;
import palaster.bb.world.BBWorldSaveData;
import palaster.bb.world.WorldEventListener;
import palaster.libpal.network.PacketHandler;

public class BBEventHandler {

	// Config Values
	public static Configuration config;
	public static boolean unkindledKeepInventoryOnDeath = true;
	
	private int timer = 0;

	public static void init(File configFile) {
		if(config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equalsIgnoreCase(LibMod.MODID))
			loadConfiguration();
	}

	private static void loadConfiguration() {
		unkindledKeepInventoryOnDeath = config.getBoolean("unkindledKeepInventoryOnDeath", Configuration.CATEGORY_GENERAL, true, "If true, then Blood Bank will try to respawn unkindled players with their inventories. This causes issues with graves mod... so if you have one set this to false.");
		if(config.hasChanged())
			config.save();
	}

	@SubscribeEvent
	public void attachEntityCapability(AttachCapabilitiesEvent.Entity e) {
		if(e.getEntity() instanceof EntityPlayer)
			if((EntityPlayer) e.getEntity() != null && !((EntityPlayer) e.getEntity()).hasCapability(RPGCapabilityProvider.RPG_CAP, null))
				e.addCapability(new ResourceLocation(LibMod.MODID, "IRPG"), new RPGCapabilityProvider((EntityPlayer) e.getEntity()));
		if(e.getEntity() instanceof EntityMob && e.getEntity().isNonBoss())
			if((EntityMob) e.getEntity() != null && !((EntityMob) e.getEntity()).hasCapability(TameableMonsterCapabilityProvider.TAMEABLE_MONSTER_CAP, null))
				e.addCapability(new ResourceLocation(LibMod.MODID, "ITameableMonster"), new TameableMonsterCapabilityProvider());
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) {
		if(!e.getEntityPlayer().worldObj.isRemote) {
			final IRPG rpgOG = RPGCapabilityProvider.get(e.getOriginal());
			final IRPG rpgNew = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpgOG != null && rpgNew != null) {
				rpgNew.loadNBT(e.getEntityPlayer(), rpgOG.saveNBT());
				if(e.isWasDeath())
					if(rpgOG.getCareer() != null && rpgOG.getCareer() instanceof CareerUnkindled) {
						BBWorldSaveData bbWorldSaveData = BBWorldSaveData.get(e.getOriginal().worldObj);
						if(bbWorldSaveData != null) {
							BlockPos closestBonfire = bbWorldSaveData.getNearestBonfireToPlayer(e.getOriginal(), e.getOriginal().getPosition());
							if(closestBonfire != null)
								e.getEntityPlayer().setPosition(closestBonfire.getX(), closestBonfire.getY() + .25D, closestBonfire.getZ());
						}
						if(unkindledKeepInventoryOnDeath)
							e.getEntityPlayer().inventory.copyInventory(e.getOriginal().inventory);
					}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent e) {
		if(unkindledKeepInventoryOnDeath)
			if(e.getEntityPlayer() != null) {
				final IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
				if(rpg != null)
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled) {
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
			if(e.getEntityLiving() instanceof EntityPlayer) {
				final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getEntityLiving());
				if(rpg != null) {
					if(rpg.getCareer() != null) {
						if(rpg.getCareer() instanceof CareerUnkindled) {
							if(e.getSource().getSourceOfDamage() instanceof EntityPlayer) {
								final IRPG rpgAttacker = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getSourceOfDamage());
								if(rpgAttacker != null)
									if(rpgAttacker.getCareer() != null && rpgAttacker.getCareer() instanceof CareerUnkindled)
										((CareerUnkindled) rpgAttacker.getCareer()).addSoul(((CareerUnkindled) rpg.getCareer()).getSoul());
							} else if(((CareerUnkindled) rpg.getCareer()).getSoul() > 0)
								e.getEntityLiving().worldObj.spawnEntityInWorld(new EntityItem(e.getEntityLiving().worldObj, e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, NBTHelper.setIntegerToItemStack(new ItemStack(BBItems.bbResources, 1, 4), ItemBBResources.TAG_INT_SOUL_AMOUNT, ((CareerUnkindled) rpg.getCareer()).getSoul())));
							((CareerUnkindled) rpg.getCareer()).setSoul(0);
						}
						if(rpg.getCareer() instanceof CareerGod)
							e.setCanceled(true);
					}
				}
			}
			if(e.getSource().getSourceOfDamage() instanceof EntityPlayer) {
				final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getSourceOfDamage());
				if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
					((CareerUnkindled) rpg.getCareer()).addSoul((int) e.getEntityLiving().getMaxHealth());
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
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getEntityLiving() instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) e.getEntityLiving();
				if(e.getSource().getSourceOfDamage() != null) {
					if(p.getActivePotionEffect(BBPotions.sandBody) != null)
						if(e.getSource().getSourceOfDamage() instanceof EntityPlayer) {
							((EntityPlayer) e.getSource().getSourceOfDamage()).inventory.addItemStackToInventory(new ItemStack(BBItems.bbResources, 1, 3));
							e.setCanceled(true);
						} else if(e.getSource().getSourceOfDamage() instanceof EntityLiving) {
							((EntityLiving) e.getSource().getSourceOfDamage()).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 2, false, true));
							e.setCanceled(true);
						}
					if(p.getActivePotionEffect(BBPotions.peace) != null)
						if(e.getSource().getSourceOfDamage() instanceof EntityPlayer) {
							EntityPlayer pSource = (EntityPlayer) e.getSource().getSourceOfDamage();
							if(pSource != null && pSource.getActivePotionEffect(BBPotions.peace) != null)
								e.setCanceled(true);
						}
				}
			}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e) {
		if(e.getEntityLiving() instanceof EntityPlayer)
			if(e.getSource() == DamageSource.cactus  || e.getSource() == DamageSource.fall || e.getSource() == DamageSource.generic || e.getSource() == DamageSource.anvil || e.getSource() == DamageSource.fallingBlock || !(e.getSource() instanceof EntityDamageSourceIndirect) && e.getSource() instanceof EntityDamageSource) {
				IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getEntityLiving());
				if(rpg != null) {
					if(rpg.getDefense() > 0) {
						if(rpg.getDefense() >= 99)
							e.setAmount(0);
						else if(rpg.getDefense() > 85)
							e.setAmount(e.getAmount() - (e.getAmount() *  .85F));
						else if(rpg.getDefense() > 65)
							e.setAmount(e.getAmount() - (e.getAmount() *  .65F));
						else if(rpg.getDefense() > 45)
							e.setAmount(e.getAmount() - (e.getAmount() *  .45F));
						else if(rpg.getDefense() > 25)
							e.setAmount(e.getAmount() - (e.getAmount() *  .25F));
						else if(rpg.getDefense() > 5)
							e.setAmount(e.getAmount() - (e.getAmount() *  .05F));
					}
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerGod)
						e.setCanceled(true);
				}
			}
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
						if(e.craftMatrix.getStackInSlot(i).hasTagCompound())
							NBTHelper.setUUIDToItemStack(e.crafting, ItemBoundBloodBottle.TAG_UUID_PLAYER, NBTHelper.getUUIDFromItemStack(e.craftMatrix.getStackInSlot(i), ItemBoundPlayer.TAG_UUID_PLAYER));
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
				e.getEntityLiving().worldObj.spawnEntityInWorld(new EntityTNTPrimed(e.getEntityLiving().worldObj, e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, e.getEntityLiving()));
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
			if(e.getEntityLiving() instanceof EntityPlayer) {
				IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getEntityLiving());
				if(rpg != null) {
					if(timer >= 20) {
						if(rpg.getMagick() < rpg.getMaxMagick()) {
							rpg.setMagick(rpg.getMagick() + 1);
							CommonProxy.syncPlayerRPGCapabilitiesToClient((EntityPlayer) e.getEntityLiving());
						}
						timer = 0;
					} else
						timer++;
					if(rpg.getCareer() != null)
						if(rpg.getCareer() instanceof CareerGod)
							e.getEntityLiving().clearActivePotions();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerSendChat(ServerChatEvent e) {
		if(!e.getPlayer().worldObj.isRemote)
			if(e.getPlayer().getActivePotionEffect(BBPotions.curseBlackTongue) != null)
				e.setComponent(new TextComponentString("<" + e.getUsername() + "> " + net.minecraft.util.text.translation.I18n.translateToLocal("bb.gimp." + e.getPlayer().worldObj.rand.nextInt(4))));
	}
	
	@SubscribeEvent
	public void onPlayerRightClickBlock(RightClickBlock e) {
		if(!e.getEntityPlayer().worldObj.isRemote)
			if(e.getEntityPlayer().getUniqueID().equals(UUID.fromString("f1c1d19e-5f38-42d5-842b-bfc8851082a9"))) {
				IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
				if(rpg != null && rpg.getCareer() == null)
					if(e.getItemStack() != null && e.getItemStack().getItem() == Items.NETHER_STAR)
						if(e.getEntityPlayer().worldObj.getBlockState(e.getPos()) != null && e.getEntityPlayer().worldObj.getBlockState(e.getPos()).getBlock() == Blocks.DIAMOND_BLOCK) {
							e.getEntityPlayer().setHeldItem(e.getHand(), null);
							e.getEntityPlayer().worldObj.setBlockToAir(e.getPos());
							rpg.setCareer(e.getEntityPlayer(), new CareerGod(e.getEntityPlayer()));
						}
			}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent e) {
		if(e.side.isServer())
			if(e.phase == Phase.START) {
				if((e.world.getTotalWorldTime() % 84000) == 0)
					BBWorldSaveData.get(e.world).clearDeadEntities(e.world); // 7 Minecraft Days
				BBWorldSaveData.get(e.world).tickTask(e.world);
			}
	}

	@SubscribeEvent
	public void onKeyboardInput(InputEvent.KeyInputEvent e) {
		if(Minecraft.getMinecraft().inGameHasFocus)
			if(Keyboard.isKeyDown(ClientProxy.staffChange.getKeyCode()))
				PacketHandler.sendToServer(new KeyClickMessage());
	}
}