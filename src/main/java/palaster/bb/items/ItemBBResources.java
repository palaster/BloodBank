package palaster.bb.items;

import java.lang.ref.WeakReference;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.EntityDemonicBankTeller;
import palaster.bb.libs.LibMod;
import palaster.bb.libs.LibNBT;

public class ItemBBResources extends Item {

    public static String[] names = new String[]{"bankContract", "bankID", "wormEater", "vampireSigil", "urn", "denseSandParticle"};

    public ItemBBResources() {
        super();
        setCreativeTab(CreativeTabBB.tabBB);
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(1);
        setUnlocalizedName("bbResources");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote) {
			if(e.getEntityLiving() instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) e.getEntityLiving();
				if(e.getSource().isMagicDamage() && p.inventory.hasItemStack(new ItemStack(BBItems.bbResources, 1, 2)))
					e.setCanceled(true);
			}
			if(e.getSource().getSourceOfDamage() instanceof EntityPlayer)
				if(((EntityPlayer )e.getSource().getSourceOfDamage()).getHeldItemMainhand() != null && ((EntityPlayer)e.getSource().getSourceOfDamage()).getHeldItemMainhand().getItem() instanceof ItemSword)
					if(((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND) != null && ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND).getItem() == new ItemStack(BBItems.bbResources, 1, 3).getItem() && ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItem(EnumHand.OFF_HAND).getItemDamage() == 3)
						BBApi.addBlood(((EntityPlayer) e.getSource().getSourceOfDamage()), (int) e.getAmount());
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
					e.getEntityPlayer().attackEntityFrom(BloodBank.proxy.bbBlood, 1f);
					BBApi.addBlood(e.getEntityPlayer(), 50);
				}
	}
    
    @SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e) {
		if(e.getLeft() != null && e.getRight() != null) {
			ItemStack copy = e.getLeft().copy();
			if(e.getLeft().getItem().isRepairable() && !(e.getLeft().hasTagCompound() && e.getLeft().getTagCompound().getBoolean(LibNBT.hasVampireSigil)))
				if(e.getRight().getItem() instanceof ItemBBResources && e.getRight().getItemDamage() == 3) {
					if(!copy.hasTagCompound())
						copy.setTagCompound(new NBTTagCompound());
					copy.getTagCompound().setBoolean(LibNBT.hasVampireSigil, true);
					e.setMaterialCost(1);
					e.setCost(1);
					e.setOutput(copy);
				}
		}
	}
    
    @SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null && e.getItemStack().hasTagCompound())
			if(e.getItemStack().getTagCompound().getBoolean(LibNBT.hasVampireSigil))
				e.getToolTip().add(I18n.format("bb.misc.vampireSigil"));
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
            if(itemStackIn.getItemDamage() == 0) {
                if(BBApi.isUndead(playerIn))
                    BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.undead"));
                else if(BBApi.getMaxBlood(playerIn) <= 0) {
                    BBApi.setMaxBlood(playerIn, 2000);
                    BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.join"));
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(this, 1, 1));
                } else
                    BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.refuse"));
            } else if(itemStackIn.getItemDamage() == 4)
            	if(!BBApi.isUndead(playerIn)) {
            		BBApi.setUndead(playerIn, true);
            		if(BBApi.getMaxBlood(playerIn) > 0) {
            			BBApi.setMaxBlood(playerIn, 0);
            			BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.bank.becomeUndead"));
            		}
            		playerIn.attackEntityFrom(DamageSource.inFire, playerIn.getMaxHealth() + 5f);
            	}
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(stack.getItemDamage() == 1) {
                if(BBApi.getMaxBlood(playerIn) > 0) {
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
    public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName(stack) + "." + names[stack.getItemDamage()]; }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        setRegistryName(new ResourceLocation(LibMod.modid, unlocalizedName));
        GameRegistry.register(this);
        return super.setUnlocalizedName(LibMod.modid + ":" + unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public static void setCustomModelResourceLocation(Item item) {
        for(int i = 0; i < names.length; i++)
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(LibMod.modid + ":" + names[i], "inventory"));
    }
}
