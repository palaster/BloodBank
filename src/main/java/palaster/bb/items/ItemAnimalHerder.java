package palaster.bb.items;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
				if(!playerIn.isBeingRidden()) {
					playerIn.startRiding(target);
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

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			if(playerIn.isBeingRidden()) {
				playerIn.dismountRidingEntity();
				return EnumActionResult.SUCCESS;
			}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
