package palaster.bb.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.entities.careers.CareerWorkshopWitch;

public class ItemHowToWitch extends ItemModStaff {

	public ItemHowToWitch(ResourceLocation rl) {
		super(rl, 0);
		powers = new String[]{"bb.hwt.0", "bb.hwt.1", "bb.hwt.2", "bb.hwt.3"};
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPlayerInteractEntity(EntityInteract e) {
		if(!e.getEntityPlayer().worldObj.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
			if(rpg != null && rpg.getCareer() != null && rpg.getCareer() instanceof CareerWorkshopWitch)
				if(!((CareerWorkshopWitch) rpg.getCareer()).isLeader())
					if(ItemModStaff.getActivePower(e.getItemStack()) == 0)
						if(e.getTarget() instanceof EntityItem) {
							ItemStack stack = ((EntityItem) e.getTarget()).getEntityItem();
							if(stack != null && stack.isItemDamaged())
								stack.damageItem(40 + (stack.getItemDamage() - stack.getMaxDamage()), e.getEntityPlayer());
						}
		}
	}
}
