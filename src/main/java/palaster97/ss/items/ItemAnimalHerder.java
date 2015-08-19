package palaster97.ss.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class ItemAnimalHerder extends ItemModSpecial {

	private final int range = 5;

	public ItemAnimalHerder() {
		super();
		setUnlocalizedName("animalHerder");
	}

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
				List<EntityAnimal> animals = playerIn.worldObj.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(playerIn.posX + range, playerIn.posY + 2, playerIn.posZ + range, playerIn.posX - range, playerIn.posY - 1, playerIn.posZ - range));
				if(animals != null) {
					for(EntityAnimal animal : animals) {
						if(animal.getEntityAttribute(SharedMonsterAttributes.attackDamage) == null) {
							animal.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
							animal.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(.5D);
						}
						if(!animal.tasks.taskEntries.contains(new EntityAIAttackOnCollide(animal, 1.0D, true)))
							animal.tasks.addTask(4, new EntityAIAttackOnCollide(animal, 1.0D, true));
						animal.setAttackTarget(target);
					}
					return true;
				}
			}
		}
		return false;
	}
}
