package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.ITameableMonster;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.entities.TameableMonsterCapability.TameableMonsterCapabilityProvider;
import palaster.bb.entities.careers.CareerMonsterTamer;
import palaster.libpal.items.ItemModSpecial;

public class ItemWhip extends ItemModSpecial {

	public ItemWhip(ResourceLocation rl) {
		super(rl);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingTarget(LivingSetAttackTargetEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getEntityLiving() instanceof EntityMob && e.getTarget() instanceof EntityPlayer) {
				ITameableMonster tm = TameableMonsterCapabilityProvider.get((EntityMob) e.getEntityLiving());
				if(tm != null && tm.getOwner() != null)
					if(tm.getOwner().equals(e.getTarget().getUniqueID()))
						((EntityMob)e.getEntityLiving()).setAttackTarget(null);
			}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getEntityLiving() instanceof EntityPlayer && e.getSource() != null && e.getSource().getEntity() != null && e.getSource().getEntity() instanceof EntityLivingBase) {
				IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) e.getEntityLiving());
				if(rpg != null && rpg.getCareer() != null && rpg.getCareer() instanceof CareerMonsterTamer) {
					List<EntityMob> mobs = e.getEntityLiving().worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(e.getEntityLiving().getPosition().add(-6, 0, -6), e.getEntityLiving().getPosition().add(6, 1, 6)));
					if(mobs != null)
						for(EntityMob mob : mobs)
							if(mob != null) {
								ITameableMonster tm = TameableMonsterCapabilityProvider.get(mob);
								if(tm != null)
									if(tm.getOwner() != null && e.getEntityLiving().getUniqueID().equals(tm.getOwner()))
										mob.setAttackTarget((EntityLivingBase) e.getSource().getEntity());
							}
				}
			}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!player.worldObj.isRemote)
			if(entity instanceof EntityLivingBase) {
				IRPG rpg = RPGCapabilityProvider.get(player);
				if(rpg != null && rpg.getCareer() != null) {
					if(rpg.getCareer() instanceof CareerMonsterTamer) {
						List<EntityMob> mobs = player.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(player.getPosition().add(-8, -1, -8), player.getPosition().add(8, 1, 8)));
						if(!mobs.isEmpty())
							for(EntityMob mob : mobs)
								if(mob != null) {
									ITameableMonster tm = TameableMonsterCapabilityProvider.get(mob);
									if(tm != null)
										if(tm.getOwner() != null && tm.getOwner().equals(player.getUniqueID()))
											mob.setAttackTarget((EntityLivingBase) entity);
								}
					}
				}
				return true;
			}
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.worldObj.isRemote) {
			IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null && rpg.getCareer() != null)
				if(rpg.getCareer() instanceof CareerMonsterTamer)
					if(target instanceof EntityMob && target.isNonBoss()) {
						ITameableMonster tm = TameableMonsterCapabilityProvider.get((EntityMob) target);
						if(tm != null)
							if(tm.getOwner() == null)
								tm.setOwner(playerIn.getUniqueID());
					}
			return true;
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
}
