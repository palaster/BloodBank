package palaster.bb.items;

import java.lang.ref.WeakReference;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.BloodBank;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.core.helpers.NBTHelper;
import palaster.bb.core.proxy.CommonProxy;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.careers.CareerUnkindled;
import palaster.libpal.items.ItemModSpecial;

public class ItemBBResources extends ItemModSpecial {
	
	public static final String[] NAMES = {"wormEater", "vampireSigil", "denseSandParticle", "soul", "itztiliSoul", "soulGem", "pinkSlip"};
	
	public static final String TAG_BOOLEAN_VAMPIRE_SIGIL = "HasVampireSigil";
	public static final String TAG_INT_SOUL_AMOUNT = "SoulAmount";

    public ItemBBResources(ResourceLocation rl) {
        super(rl);
        setHasSubtypes(true);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			if(e.getEntityLiving() instanceof EntityPlayer)
				if(e.getSource().isMagicDamage() && ((EntityPlayer) e.getEntityLiving()).inventory.hasItemStack(new ItemStack(BBItems.bbResources, 1, 0)))
					e.setCanceled(true);
			if(e.getSource().getEntity() instanceof EntityPlayer)
				if(((EntityPlayer )e.getSource().getSourceOfDamage()).getHeldItemMainhand() != null && ((EntityPlayer)e.getSource().getSourceOfDamage()).getHeldItemMainhand().getItem() instanceof ItemSword)
					if(((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND) != null && ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND).getItem() == new ItemStack(BBItems.bbResources, 1, 1).getItem() && ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND).getItemDamage() == 1) {
						final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getSource().getSourceOfDamage());
						if(rpg != null)
							if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer)
								((CareerBloodSorcerer) rpg.getCareer()).addBlood((int) e.getAmount() * 50);
					}
		}
	}
    
    @SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if(!e.player.worldObj.isRemote)
			if(e.phase == TickEvent.Phase.START)
				if(e.player.inventory.hasItemStack(new ItemStack(BBItems.bbResources, 1, 0)))
					e.player.clearActivePotions();
	}
    
    @SubscribeEvent
    public void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract e) {
    	if(!e.getWorld().isRemote && e.getSide() == Side.SERVER)
    		if(e.getTarget() instanceof EntitySkeleton)
    			if(((EntitySkeleton) e.getTarget()).func_189771_df() == SkeletonType.WITHER)
    				if(e.getHand() == EnumHand.OFF_HAND) {
    		    		if(e.getEntityPlayer().getHeldItem(e.getHand()) != null && e.getEntityPlayer().getHeldItem(e.getHand()).getItem() == Items.DIAMOND) {
    		    			if(e.getEntityPlayer().getHeldItemOffhand().stackSize > 1) {
    							e.getEntityPlayer().getHeldItemOffhand().stackSize--;
    		            		ItemStack soulGem = new ItemStack(BBItems.bbResources, 1, 5);
    		            		if(!e.getEntityPlayer().inventory.addItemStackToInventory(soulGem))
    		            			e.getWorld().spawnEntityInWorld(new EntityItem(e.getWorld(), e.getEntityPlayer().posX, e.getEntityPlayer().posY, e.getEntityPlayer().posZ, soulGem));
    		            	} else
    		            		e.getEntityPlayer().setHeldItem(EnumHand.OFF_HAND, new ItemStack(BBItems.bbResources, 1, 5));
    		    		}
    		    	} else if(e.getHand() == EnumHand.MAIN_HAND) {
    		    		if(e.getEntityPlayer().getHeldItem(e.getHand()) != null && e.getEntityPlayer().getHeldItem(e.getHand()).getItem() == Items.DIAMOND) {
    		    			if(e.getEntityPlayer().getHeldItemMainhand().stackSize > 1) {
    							e.getEntityPlayer().getHeldItemMainhand().stackSize--;
    		            		ItemStack soulGem = new ItemStack(BBItems.bbResources, 1, 5);
    		            		if(!e.getEntityPlayer().inventory.addItemStackToInventory(soulGem))
    		            			e.getWorld().spawnEntityInWorld(new EntityItem(e.getWorld(), e.getEntityPlayer().posX, e.getEntityPlayer().posY, e.getEntityPlayer().posZ, soulGem));
    		            	} else
    		            		e.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, new ItemStack(BBItems.bbResources, 1, 5));
    		    		}
    		    	}
    }
    
    @SubscribeEvent
	public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem e) {
		if(!e.getWorld().isRemote && e.getSide().isServer())
			if(e.getHand() == EnumHand.MAIN_HAND)
				if(e.getEntityPlayer().getHeldItemMainhand() != null && e.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemSword)
					if(e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND) != null && e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == new ItemStack(BBItems.bbResources, 1, 1).getItem() && e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItemDamage() == 1) {
						final IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
						if(rpg != null)
							if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
								((CareerBloodSorcerer) rpg.getCareer()).addBlood(100);
								e.getEntityPlayer().attackEntityFrom(BloodBank.proxy.bbBlood, 1f);
							}
					}
	}
    
    @SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e) {
		if(e.getLeft() != null && e.getRight() != null) {
			ItemStack copy = e.getLeft().copy();
			if(e.getLeft().getItem().isRepairable() && !(NBTHelper.getBooleanFromItemStack(e.getLeft(), TAG_BOOLEAN_VAMPIRE_SIGIL)))
				if(e.getRight().getItem() instanceof ItemBBResources && e.getRight().getItemDamage() == 1) {
					e.setMaterialCost(1);
					e.setCost(1);
					e.setOutput(NBTHelper.setBooleanToItemStack(copy, TAG_BOOLEAN_VAMPIRE_SIGIL, true));
				}
		}
	}
    
    @SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null)
			if(NBTHelper.getBooleanFromItemStack(e.getItemStack(), TAG_BOOLEAN_VAMPIRE_SIGIL))
				e.getToolTip().add(I18n.format("bb.misc.vampireSigil"));
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    	if(stack.hasTagCompound() && stack.getItemDamage() == 3)
    		tooltip.add(I18n.format("bb.undead.soulsAmount") + " : " + NBTHelper.getIntegerFromItemStack(stack, TAG_INT_SOUL_AMOUNT));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
        	if(itemStackIn.getItemDamage() == 3) {
            	if(itemStackIn.hasTagCompound()) {
            		final IRPG rpg = RPGCapabilityProvider.get(playerIn);
            		if(rpg != null)
            			if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled) {
            				((CareerUnkindled) rpg.getCareer()).addSoul(itemStackIn.getTagCompound().getInteger(TAG_INT_SOUL_AMOUNT));
            				playerIn.setHeldItem(hand, null);
            				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
                		}
            	}
            } else if(itemStackIn.getItemDamage() == 6) {
            	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
        		if(rpg != null)
        			if(rpg.getCareer() != null) {
        				rpg.setCareer(playerIn, null);
        				CommonProxy.syncPlayerRPGCapabilitiesToClient(playerIn);
        				BBPlayerHelper.sendChatMessageToPlayer(playerIn, net.minecraft.util.text.translation.I18n.translateToLocal("bb.career.fired"));
        				playerIn.setHeldItem(hand, null);
        				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
            		}
            }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	if(!worldIn.isRemote)
    		if(stack.getItemDamage() == 2 && entityIn instanceof EntityPlayer)
    			if(((EntityPlayer) entityIn).getActivePotionEffect(MobEffects.SLOWNESS) != null) {
    				WeakReference<PotionEffect> pe = new WeakReference<PotionEffect>(((EntityPlayer) entityIn).getActivePotionEffect(MobEffects.SLOWNESS));
    				((EntityPlayer) entityIn).removeActivePotionEffect(MobEffects.SLOWNESS);
    				if(pe != null && pe.get() != null)
    					((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, pe.get().getAmplifier() + 1, false, true));
    			} else 
    				((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 0, false, true));    				
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(int i = 0; i < NAMES.length; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
	public static void setCustomModelResourceLocation(Item item) {
    	for(int i = 0; i < NAMES.length; i++)
    		ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation("bb:" + NAMES[i], "inventory"));
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName() + "." + stack.getItemDamage(); }
}
