package palaster97.ss.core.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.blocks.BlockWorldManipulator;
import palaster97.ss.blocks.tile.TileEntityWorldManipulator;
import palaster97.ss.core.helpers.SSItemStackHelper;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemModStaff;
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
	
	@SubscribeEvent
	public void onPlayerInteract(EntityInteractEvent e) {
		if(!e.entityPlayer.worldObj.isRemote) {
			if(e.entityPlayer.getCurrentEquippedItem() != null && e.entityPlayer.getCurrentEquippedItem().getItem() == Items.bucket)
				if(e.entityPlayer.getItemInUse() != null && e.entityPlayer.getItemInUse().getItem() == Items.bucket && !e.entityPlayer.capabilities.isCreativeMode)
					if(e.target instanceof EntityPlayer)
						if(((EntityPlayer) e.target).getUniqueID().toString().equals("f1c1d19e-5f38-42d5-842b-bfc8851082a9"))
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
			if(e.entityLiving.isPotionActive(ScreamingSouls.proxy.death))
				if(e.entityLiving.getActivePotionEffect(ScreamingSouls.proxy.death).getDuration() == 0)
					e.entityLiving.setDead();
	}

	// TODO: Fixed NullPointer when block being refrenced is broken and hud tries to draw the block.
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
							ItemStack stack = SSItemStackHelper.getItemStackFromInventory(DimensionManager.getWorld(Minecraft.getMinecraft().theWorld.provider.getDimensionId()), pos, 0);
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
				if(Minecraft.getMinecraft().fontRendererObj != null && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemModStaff)
					if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().hasTagCompound()) {
						ItemStack staff = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
						String power = StatCollector.translateToLocal(((ItemModStaff) staff.getItem()).powers[ItemModStaff.getActivePower(staff)]);
						Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("ss.staff.active") + ": " + power, 2, 2, 0);
					}
		}
	}
}