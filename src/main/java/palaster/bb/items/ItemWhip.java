package palaster.bb.items;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.ITameableMonster;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.entities.TameableMonsterCapability.TameableMonsterCapabilityProvider;
import palaster.bb.entities.careers.CareerMonsterTamer;

public class ItemWhip extends ItemModSpecial {

	public ItemWhip() {
		super();
		setUnlocalizedName("whip");
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!player.worldObj.isRemote)
			if(entity instanceof EntityLivingBase) {
				IRPG rpg = RPGCapabilityProvider.get(player);
				if(rpg != null && rpg.getCareer() != null) {
					if(rpg.getCareer() instanceof CareerMonsterTamer) {
						List<EntityMob> mobs = player.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(player.getPosition().add(-6, -1, -6), player.getPosition().add(6, 1, 6)));
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
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(playerIn.isSneaking()) {
				IBlockState state = worldIn.getBlockState(pos);
				if(state != null && state.getBlock() == Blocks.BED) {
					IRPG rpg =  RPGCapabilityProvider.get(playerIn);
					if(rpg != null && rpg.getCareer() == null)
						rpg.setCareer(new CareerMonsterTamer());
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
