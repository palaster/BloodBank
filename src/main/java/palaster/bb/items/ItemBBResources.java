package palaster.bb.items;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.entities.UndeadCapability.UndeadCapabilityProvider;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.EntityDemonicBankTeller;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.careers.CareerUndead;

public class ItemBBResources extends ItemModSpecial {
	
	public static String tag_hasVampireSigil = "HasVampireSigil";
	public static String tag_soulAmount = "SoulAmount";
	
	private static final int subTypes = 8;

    public ItemBBResources() {
        super();
        setMaxDamage(subTypes - 1);
        setUnlocalizedName("bbResources");
        addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) { return stack.getItemDamage(); }
        });
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			if(e.getEntityLiving() instanceof EntityPlayer)
				if(e.getSource().isMagicDamage() && ((EntityPlayer) e.getEntityLiving()).inventory.hasItemStack(new ItemStack(BBItems.bbResources, 1, 2)))
					e.setCanceled(true);
			if(e.getSource().getSourceOfDamage() instanceof EntityPlayer)
				if(((EntityPlayer )e.getSource().getSourceOfDamage()).getHeldItemMainhand() != null && ((EntityPlayer)e.getSource().getSourceOfDamage()).getHeldItemMainhand().getItem() instanceof ItemSword)
					if(((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND) != null && ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND).getItem() == new ItemStack(BBItems.bbResources, 1, 3).getItem() && ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND).getItemDamage() == 3) {
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
				for(PotionEffect potionEffect : e.player.getActivePotionEffects())
					if(potionEffect != null)
						if(e.player.inventory.hasItemStack(new ItemStack(BBItems.bbResources, 1, 2)))
							e.player.removePotionEffect(potionEffect.getPotion());
	}
    
    @SubscribeEvent
	public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem e) {
		if(!e.getWorld().isRemote && e.getSide().isServer())
			if(e.getEntityPlayer().getHeldItemMainhand() != null && e.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemSword)
				if(e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND) != null && e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == new ItemStack(BBItems.bbResources, 1, 3).getItem() && e.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItemDamage() == 3) {
					final IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
					if(rpg != null)
						if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
							((CareerBloodSorcerer) rpg.getCareer()).addBlood(50);
							e.getEntityPlayer().attackEntityFrom(BloodBank.proxy.bbBlood, 2f);
						}
				}
	}
    
    @SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e) {
		if(e.getLeft() != null && e.getRight() != null) {
			ItemStack copy = e.getLeft().copy();
			if(e.getLeft().getItem().isRepairable() && !(e.getLeft().hasTagCompound() && e.getLeft().getTagCompound().getBoolean(tag_hasVampireSigil)))
				if(e.getRight().getItem() instanceof ItemBBResources && e.getRight().getItemDamage() == 3) {
					if(!copy.hasTagCompound())
						copy.setTagCompound(new NBTTagCompound());
					copy.getTagCompound().setBoolean(tag_hasVampireSigil, true);
					e.setMaterialCost(1);
					e.setCost(1);
					e.setOutput(copy);
				}
		}
	}
    
    @SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null && e.getItemStack().hasTagCompound())
			if(e.getItemStack().getTagCompound().getBoolean(tag_hasVampireSigil))
				e.getToolTip().add(I18n.format("bb.misc.vampireSigil"));
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    	if(stack.hasTagCompound() && stack.getItemDamage() == 6)
    		tooltip.add(I18n.format("bb.undead.soulsAmount") + " : " + stack.getTagCompound().getInteger(tag_soulAmount));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
            if(itemStackIn.getItemDamage() == 0) {
            	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
				if(rpg != null && undead != null) {
	                if(undead.isUndead())
	                    BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.undead"));
	                else if(rpg.getCareer() == null || !(rpg.getCareer() instanceof CareerBloodSorcerer)) {
	                    rpg.setCareer(new CareerBloodSorcerer());
	                    ((CareerBloodSorcerer) rpg.getCareer()).setMaxBlood(2000);
	                    BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.join"));
	                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(this, 1, 1));
	                } else
	                    BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.refuse"));
				}
            } else if(itemStackIn.getItemDamage() == 4) {
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            	if(undead != null)
            		if(!undead.isUndead()) {
                		undead.setUndead(true);
                		final IRPG rpg = RPGCapabilityProvider.get(playerIn);
        				if(rpg != null)
        					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
        						rpg.setCareer(new CareerUndead());
                    			BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.becomeUndead"));
                    		}
                		playerIn.attackEntityFrom(DamageSource.inFire, playerIn.getMaxHealth() + 5f);
                		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, null);
                	}
            } else if(itemStackIn.getItemDamage() == 6)
            	if(itemStackIn.hasTagCompound()) {
            		final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            		if(undead != null)
            			if(undead.isUndead()) {
            				undead.addSoul(itemStackIn.getTagCompound().getInteger(tag_soulAmount));
                			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, null);
                		}
            	}
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(stack.getItemDamage() == 1) {
            	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
				if(rpg != null)
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
	                    EntityDemonicBankTeller dbt = new EntityDemonicBankTeller(worldIn);
	                    dbt.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
	                    worldIn.spawnEntityInWorld(dbt);
	                    playerIn.setHeldItem(hand, null);
	                    return EnumActionResult.SUCCESS;
	                }
            }
        return EnumActionResult.PASS;
    }
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	if(!worldIn.isRemote)
    		if(stack.getItemDamage() == 5 && entityIn instanceof EntityPlayer)
    			if(((EntityPlayer) entityIn).getActivePotionEffect(MobEffects.SLOWNESS) != null) {
    				WeakReference<PotionEffect> pe = new WeakReference<PotionEffect>(((EntityPlayer) entityIn).getActivePotionEffect(MobEffects.SLOWNESS));
    				((EntityPlayer) entityIn).removeActivePotionEffect(MobEffects.SLOWNESS);
    				if(pe != null && pe.get() != null)
    					((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, pe.get().getAmplifier() + 1, false, true));
    			} else 
    				((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 0, false, true));    				
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(int i = 0; i < subTypes; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }
    
    @Override
	public boolean showDurabilityBar(ItemStack stack) { return false; }
	
	@SuppressWarnings("deprecation")
	@Override
	public String getItemStackDisplayName(ItemStack stack) { return ("" + net.minecraft.util.text.translation.I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + "." + stack.getItemDamage() + ".name")).trim(); }
}
