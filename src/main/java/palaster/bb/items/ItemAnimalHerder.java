package palaster.bb.items;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class ItemAnimalHerder extends ItemModSpecial {

	private final int range = 5;

	public ItemAnimalHerder() {
		super();
		setUnlocalizedName("animalHerder");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.worldObj.isRemote) {
			if(target instanceof EntityAnimal) {
				if(playerIn.getControllingPassenger() == null) {
					target.startRiding(playerIn, true);
					return true;
				} else {
					target.dismountEntity(playerIn);
					playerIn.dismountRidingEntity();
					return true;
				}
			} else if(target instanceof EntityLiving && !(target instanceof EntityAnimal)) {
				List<EntityAnimal> animals = playerIn.worldObj.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(playerIn.posX + range, playerIn.posY + 2, playerIn.posZ + range, playerIn.posX - range, playerIn.posY - 1, playerIn.posZ - range));
				if(animals != null) {
					for(EntityAnimal animal : animals) {
						if(animal.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null) {
							animal.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
							animal.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(.5D);
						}
						if(!animal.tasks.taskEntries.contains(new EntityAIAttackMelee(animal, 1.0D, true)))
							animal.tasks.addTask(4, new EntityAIAttackMelee(animal, 1.0D, true));
						animal.setAttackTarget(target);
					}
					return true;
				}
			}
		}
		return false;
	}
}
