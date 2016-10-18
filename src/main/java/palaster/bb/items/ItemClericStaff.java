package palaster.bb.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.items.IPurified;
import palaster.bb.entities.careers.CareerCleric;
import palaster.bb.entities.effects.BBPotions;

public class ItemClericStaff extends ItemModStaff implements IPurified {
	
	public ItemClericStaff(ResourceLocation rl) {
		super(rl);
		powers = new String[]{"bb.clericStaff.0", "bb.clericStaff.1", "bb.clericStaff.2", "bb.clericStaff.3"};
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			switch(getActivePower(stack)) {
				case 0: {
					List<EntityPlayer> players = worldIn.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-5, 0, -5), pos.add(5, 5, 5)));
					if(players != null)
						for(EntityPlayer player : players)
							if(player != null && player.getActivePotionEffect(BBPotions.peace) == null)
								player.addPotionEffect(new PotionEffect(BBPotions.peace, 6000, 0, false, true));
					stack.damageItem(64, playerIn);
					return EnumActionResult.SUCCESS;
				}
			}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.worldObj.isRemote)
			if(target instanceof EntityPlayer)
				switch(getActivePower(stack)) {
					case 1: {
						target.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 3000, 2, false, true));
						stack.damageItem(64, playerIn);
						break;
					}
					case 2: {
						target.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3000, 2, false, true));
						stack.damageItem(64, playerIn);
						break;
					}
					case 3: {
						target.addPotionEffect(new PotionEffect(MobEffects.SPEED, 3000, 2, false, true));
						stack.damageItem(64, playerIn);
						break;
					}
				}
		return true;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!player.worldObj.isRemote)
			if(entity instanceof EntityLiving) {
				final IRPG rpg = RPGCapabilityProvider.get(player);
				if(rpg != null)
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerCleric)
						if(((EntityLiving) entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
							return ((EntityLiving) entity).attackEntityFrom(DamageSource.causePlayerDamage(player), (float) (player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + 5));
			}
		return super.onLeftClickEntity(stack, player, entity);
	}
}
