package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

public class ItemAnimalHerder extends ItemModSpecial {

	public ItemAnimalHerder() {
		super();
		setUnlocalizedName("animalHerder");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
		if(!playerIn.worldObj.isRemote) {
			if(target instanceof EntityAnimal) {
				if(playerIn.riddenByEntity == null) {
					((EntityAnimal) target).mountEntity(playerIn);
					playerIn.riddenByEntity = (EntityAnimal) target;
					return true;
				} else {
					((EntityAnimal) target).dismountEntity(playerIn);
					playerIn.riddenByEntity = null;
					return true;
				}
			} else if(target instanceof EntityLivingBase && !(target instanceof EntityAnimal)) {
				List<EntityAnimal> animals = playerIn.worldObj.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(playerIn.posX + 7, playerIn.posY + 2, playerIn.posZ + 7, playerIn.posX - 7, playerIn.posY, playerIn.posZ - 7));
				if(animals != null) {
					for(EntityAnimal animal : animals) {
						// TODO: Figure way to get mobs that weren't meant to deal dmg. to deal dmg.
						animal.tasks.addTask(4, new EntityAIAttackOnCollide(animal, 1.0D, true));
//						animal.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
//						animal.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(.5D);
						animal.setAttackTarget(target);
					}
					return true;
				}
			}
		}
		return false;
	}
}
