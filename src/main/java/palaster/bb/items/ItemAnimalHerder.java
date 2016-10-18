package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import palaster.libpal.items.ItemModSpecial;

public class ItemAnimalHerder extends ItemModSpecial {

	private final int range = 5;

	public ItemAnimalHerder(ResourceLocation rl) {
		super(rl);
		MinecraftForge.EVENT_BUS.register(this);
	}

	// TODO: This doesn't work in single-player worlds.
	@SubscribeEvent
	public void onPlayerLogOff(PlayerLoggedOutEvent e) {
		if(e.player.getPassengers() != null)
			for(Entity entity : e.player.getPassengers())
				entity.dismountRidingEntity();
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(target instanceof EntityAnimal) {
			target.startRiding(playerIn, true);
			return  true;
		} else if(target instanceof EntityLiving && !(target instanceof EntityAnimal)) {
			if(!playerIn.worldObj.isRemote) {
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(playerIn.getPassengers() != null)
			for(Entity entity : playerIn.getPassengers())
				if(entity instanceof EntityAnimal) {
					((EntityAnimal) entity).dismountRidingEntity();
					return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
				}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
