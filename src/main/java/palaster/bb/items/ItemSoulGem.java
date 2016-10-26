package palaster.bb.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import palaster.libpal.items.ItemModSpecial;

public class ItemSoulGem extends ItemModSpecial {

	public ItemSoulGem(ResourceLocation rl) {
		super(rl);
		MinecraftForge.EVENT_BUS.register(this);
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
    		            		ItemStack soulGem = new ItemStack(this);
    		            		if(!e.getEntityPlayer().inventory.addItemStackToInventory(soulGem))
    		            			e.getWorld().spawnEntityInWorld(new EntityItem(e.getWorld(), e.getEntityPlayer().posX, e.getEntityPlayer().posY, e.getEntityPlayer().posZ, soulGem));
    		            	} else
    		            		e.getEntityPlayer().setHeldItem(EnumHand.OFF_HAND, new ItemStack(this));
    		    		}
    		    	} else if(e.getHand() == EnumHand.MAIN_HAND) {
    		    		if(e.getEntityPlayer().getHeldItem(e.getHand()) != null && e.getEntityPlayer().getHeldItem(e.getHand()).getItem() == Items.DIAMOND) {
    		    			if(e.getEntityPlayer().getHeldItemMainhand().stackSize > 1) {
    							e.getEntityPlayer().getHeldItemMainhand().stackSize--;
    		            		ItemStack soulGem = new ItemStack(this);
    		            		if(!e.getEntityPlayer().inventory.addItemStackToInventory(soulGem))
    		            			e.getWorld().spawnEntityInWorld(new EntityItem(e.getWorld(), e.getEntityPlayer().posX, e.getEntityPlayer().posY, e.getEntityPlayer().posZ, soulGem));
    		            	} else
    		            		e.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, new ItemStack(this));
    		    		}
    		    	}
    }
}
