package palaster.bb.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IVampiric;

public class ItemStaffEfreet extends ItemModStaff implements IVampiric {
	
	public ItemStaffEfreet(ResourceLocation rl) {
		super(rl);
		powers = new String[]{"bb.staff.efreet.0"};
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) { return 40; }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) { return EnumAction.BOW; }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if(ItemModStaff.getActivePower(stack) == 0) {
			for(EntityLivingBase entity : entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entityLiving.posX - 8, entityLiving.posY - 3, entityLiving.posZ - 8, entityLiving.posX + 8, entityLiving.posY + 8, entityLiving.posZ + 8)))
				if(entity != entityLiving)
					if(!entityLiving.worldObj.isRemote) {
						entity.attackEntityFrom(DamageSource.onFire, 4f);
						entity.setFire(2);
						stack.damageItem(1, entityLiving);
					}
			if(entityLiving.worldObj.isRemote)
				for(int i = -4; i <= 4; ++i)
					for(int j = -4; j <= 4; ++j)
						for(int k = 0; k <= 1; ++k)
							entityLiving.worldObj.spawnParticle(EnumParticleTypes.FLAME, entityLiving.posX + .5D, entityLiving.posY + 1D, entityLiving.posZ + .5D, (double)((float)i + entityLiving.worldObj.rand.nextFloat()) - 0.5D, (double)((float)k - entityLiving.worldObj.rand.nextFloat() - 1.0F), (double)((float)j + entityLiving.worldObj.rand.nextFloat()) - 0.5D);
		}
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
