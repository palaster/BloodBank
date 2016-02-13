package palaster.bb.core.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
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
import palaster.bb.blocks.BlockWorldManipulator;
import palaster.bb.blocks.tile.TileEntityWorldManipulator;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.entities.extended.BBExtendedPlayer;
import palaster.bb.entities.knowledge.BBKnowledge;
import palaster.bb.items.*;
import palaster.bb.libs.LibMod;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.client.SyncPlayerPropsMessage;
import palaster.bb.network.server.MiddleClickMessage;
import palaster.bb.world.WorldManager;

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
		if(e.modID.equalsIgnoreCase(LibMod.modid))
			loadConfiguration();
	}

	private static void loadConfiguration() {
		if(config.hasChanged())
			config.save();
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e) {
		if(e.entity instanceof EntityPlayer)
			if(BBExtendedPlayer.get((EntityPlayer) e.entity) == null)
				BBExtendedPlayer.register((EntityPlayer) e.entity);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e) {
		if(e.entity instanceof EntityPlayerMP)
			PacketHandler.sendTo(new SyncPlayerPropsMessage((EntityPlayer) e.entity), (EntityPlayerMP) e.entity);		
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) { BBExtendedPlayer.get(e.entityPlayer).copy(BBExtendedPlayer.get(e.original)); }

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		if(!e.entityLiving.worldObj.isRemote)
			for(Entity entity : e.entityLiving.worldObj.loadedEntityList) {
				if(entity instanceof EntityPlayer)
					if(BBExtendedPlayer.get((EntityPlayer) entity) != null)
						if(BBExtendedPlayer.get((EntityPlayer) entity).getLinked() != null && BBExtendedPlayer.get((EntityPlayer) entity).getLinked().getUniqueID() == e.entityLiving.getUniqueID()) {
							BBExtendedPlayer.get((EntityPlayer) entity).linkEntity(null);
							continue;
						}
			}
	}

	@SubscribeEvent
	public void onDropItem(ItemTossEvent e) {
		if(!e.player.worldObj.isRemote)
			if(e.entityItem != null && e.entityItem.getEntityItem() != null && e.entityItem.getEntityItem().getItem() instanceof BBArmor)
				if(e.entityItem.getEntityItem().getItem() == BBItems.boundHelmet || e.entityItem.getEntityItem().getItem() == BBItems.boundChestplate || e.entityItem.getEntityItem().getItem() == BBItems.boundLeggings || e.entityItem.getEntityItem().getItem() == BBItems.boundBoots) {
					if(BBItemStackHelper.getItemStackFromItemStack(e.entityItem.getEntityItem()) != null) {
						EntityItem hold = new EntityItem(e.player.worldObj, e.entityItem.posX, e.entityItem.posY, e.entityItem.posZ, BBItemStackHelper.getItemStackFromItemStack(e.entityItem.getEntityItem()));
						e.player.worldObj.spawnEntityInWorld(hold);
					}
					e.setCanceled(true);
				}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.entityLiving.worldObj.isRemote && e.entityLiving instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.entityLiving;
			if(e.source == DamageSource.drown)
				if(p.inventory.hasItem(BBItems.trident))
					for(int i = 0; i < 9; i++)
						if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() instanceof ItemTrident) {
							e.setCanceled(true);
							p.inventory.getStackInSlot(i).damageItem(1, p);
						}
			if(e.source.getEntity() != null) {
				BBExtendedPlayer props = BBExtendedPlayer.get(p);
				if(props != null)
					if(props.getLinked() != null) {
						EntityLiving link = props.getLinked();
						link.attackEntityFrom(BBExtendedPlayer.bbBlood, e.ammount);
						e.setCanceled(true);
					}
			}
		}
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		if(!e.world.isRemote)
			e.world.addWorldAccess(new WorldManager());
	}
	
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.itemStack != null && e.itemStack.hasTagCompound() && e.itemStack.getTagCompound().getBoolean("HasTapeHeart")) {
			String temp = e.toolTip.get(e.toolTip.size() - 1);
			e.toolTip.set(e.toolTip.size() - 1, StatCollector.translateToLocal("bb.misc.tapeHeart"));
			e.toolTip.add(temp);
		}
	}
	
	@SubscribeEvent
	public void onPlayerInteract(EntityInteractEvent e) {
		if(!e.entityPlayer.worldObj.isRemote) {
			if(e.entityPlayer.getCurrentEquippedItem() != null && e.entityPlayer.getCurrentEquippedItem().getItem() == Items.bucket)
				if(e.entityPlayer.getItemInUse() != null && e.entityPlayer.getItemInUse().getItem() == Items.bucket && !e.entityPlayer.capabilities.isCreativeMode)
					if(e.target instanceof EntityPlayer)
						if(e.target.getUniqueID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9"))
							if(e.entityPlayer.getItemInUse().stackSize-- == 1)
								e.entityPlayer.inventory.setInventorySlotContents(e.entityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
				            else if(!e.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket)))
				            	e.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
			if(e.entityPlayer.getUniqueID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9") && e.entityPlayer.getCurrentEquippedItem() == null)
				if(e.target instanceof EntityCow)
					if(((EntityCow) e.target).isInLove()) {
						EntityCow baby = new EntityCow(e.entityPlayer.worldObj);
						baby.setGrowingAge(-24000);
						baby.setPosition(e.target.posX, e.target.posY + .25D, e.target.posZ);
						e.entityPlayer.worldObj.spawnEntityInWorld(baby);
						((EntityCow) e.target).resetInLove();
					}
		}
	}

	@SubscribeEvent
	public void updateLivingEntity(LivingEvent.LivingUpdateEvent e) {
		if(!e.entityLiving.worldObj.isRemote)
			if(e.entityLiving.isPotionActive(BloodBank.proxy.death))
				if(e.entityLiving.getActivePotionEffect(BloodBank.proxy.death).getDuration() <= 1)
					e.entityLiving.setDead();
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent e) {
		if(!e.world.isRemote)
			for(TileEntity te : e.world.loadedTileEntityList)
				if(te != null && te instanceof TileEntityWorldManipulator) {
					TileEntityWorldManipulator wm = (TileEntityWorldManipulator) te;
					if(wm.getStackInSlot(0) != null && wm.getStackInSlot(0).getItem() instanceof ItemWorldBinder && wm.getStackInSlot(0).hasTagCompound()) {
						int[] temp = wm.getStackInSlot(0).getTagCompound().getIntArray("WorldPos");
						BlockPos wmBlockPos = new BlockPos(temp[0], temp[1], temp[2]);
						if(e.pos.equals(wmBlockPos))
							wm.getStackInSlot(0).getTagCompound().setBoolean("IsSet", false);
					}
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
			if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
				MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
				if(mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					BlockPos pos = mop.getBlockPos();
					IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(pos);
					if(blockState != null && blockState.getBlock() instanceof BlockWorldManipulator) {
						TileEntityWorldManipulator wsm = (TileEntityWorldManipulator) Minecraft.getMinecraft().theWorld.getTileEntity(pos);
						if(wsm != null) {
							ItemStack stack = BBItemStackHelper.getItemStackFromInventory(DimensionManager.getWorld(Minecraft.getMinecraft().theWorld.provider.getDimensionId()), pos, 0);
							if(stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean("IsSet")) {
								int[] temp = stack.getTagCompound().getIntArray("WorldPos");
								BlockPos pos1 = new BlockPos(temp[0], temp[1], temp[2]);
								if(Minecraft.getMinecraft().theWorld.provider.getDimensionId() == stack.getTagCompound().getInteger("DimID")) {
									IBlockState blockState1 = DimensionManager.getWorld(Minecraft.getMinecraft().theWorld.provider.getDimensionId()).getBlockState(pos1);
									if(blockState1 != null && blockState1.getBlock() != null || blockState1 != null && blockState1.getBlock() != Blocks.air)
										Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(blockState1.getBlock()), 5, 5);
								} else {
									World world = DimensionManager.getWorld(stack.getTagCompound().getInteger("DimID"));
									if(world != null)
										if(world.getBlockState(pos1) != null && world.getBlockState(pos1).getBlock() != null)
											Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(world.getBlockState(pos1).getBlock()), 5, 5);
								}
							}
						}
					}
				}
			} else if(event.type == RenderGameOverlayEvent.ElementType.TEXT)
				if(Minecraft.getMinecraft().fontRendererObj != null && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null) {
					if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemModStaff && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().hasTagCompound()) {
						ItemStack staff = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
						String power = StatCollector.translateToLocal(((ItemModStaff) staff.getItem()).powers[ItemModStaff.getActivePower(staff)]);
						Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("bb.staff.active") + ": " + power, 2, 2, 0);
					}
					if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBookBlood && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().hasTagCompound()) {
						ItemStack book = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
						String spell = StatCollector.translateToLocal("bb.knowledgePiece") + ": " + StatCollector.translateToLocal(BBKnowledge.getKnowledgePiece(book.getTagCompound().getInteger("Knowledge Piece")).getName());
						Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("bb.kp.active") + ": " + spell, 2, 2, 0x8A0707);
					}
				}
		}
	}
}