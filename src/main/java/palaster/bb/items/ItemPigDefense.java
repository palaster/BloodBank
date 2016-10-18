package palaster.bb.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.api.capabilities.items.IVampiric;
import palaster.bb.entities.effects.BBPotions;
import palaster.libpal.items.ItemModSpecial;

public class ItemPigDefense extends ItemModSpecial implements IVampiric {

	public ItemPigDefense(ResourceLocation rl) {
		super(rl);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getEntityLiving() instanceof EntityPlayer && e.getSource().getSourceOfDamage() != null && e.getSource().getSourceOfDamage() instanceof EntityLivingBase) {
				EntityPlayer p = (EntityPlayer) e.getEntityLiving();
				for(int i = 0; i < p.inventory.getSizeInventory(); i++)
					if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() instanceof ItemPigDefense) {
						EntityPigZombie pz = new EntityPigZombie(e.getEntityLiving().worldObj);
						pz.setPosition(e.getEntityLiving().posX, e.getEntityLiving().posY + 1, e.getEntityLiving().posZ);
						e.getEntityLiving().worldObj.spawnEntityInWorld(pz);
						pz.setAttackTarget((EntityLivingBase) e.getSource().getSourceOfDamage());
						pz.addPotionEffect(new PotionEffect(BBPotions.instantDeath, 600, 0, false, true));
					}
			}
	}
}
