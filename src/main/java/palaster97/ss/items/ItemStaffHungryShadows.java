package palaster97.ss.items;

import palaster97.ss.entities.EntityShadow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemStaffHungryShadows extends ItemModStaff {

	public ItemStaffHungryShadows() {
		super();
		powers = new String[]{"ss.staff.hs.0", "ss.staff.hs.1", "ss.staff.hs.2", "ss.staff.hs.3", "ss.staff.hs.4"};
		setUnlocalizedName("staffHungryShadows");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(ItemModStaff.getActivePower(stack) == 3) {
				EntityShadow shadow = new EntityShadow(worldIn);
				shadow.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
				worldIn.spawnEntityInWorld(shadow);
				stack.damageItem(24, playerIn);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
		if(!playerIn.worldObj.isRemote) {
			if(ItemModStaff.getActivePower(stack) == 0) {
				target.addPotionEffect(new PotionEffect(18, 2400));
				stack.damageItem(12, playerIn);
				return true;
			} else if(ItemModStaff.getActivePower(stack) == 1) {
				target.attackEntityFrom(DamageSource.magic, 4f);
				playerIn.heal(4f);
				stack.damageItem(12, playerIn);
				return true;
			} else if(ItemModStaff.getActivePower(stack) == 2 && target instanceof EntityPlayer) {
				int targetExp = ((EntityPlayer) target).experienceTotal;
				((EntityPlayer) target).addExperience(-(targetExp/ 4));
				playerIn.addExperience(targetExp/4);
				stack.damageItem(24, playerIn);
				return true;
			}
		}
		return false;
	}
}
