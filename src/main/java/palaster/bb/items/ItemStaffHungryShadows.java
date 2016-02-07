package palaster.bb.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class ItemStaffHungryShadows extends ItemModStaff {

	public ItemStaffHungryShadows() {
		super();
		powers = new String[]{"bb.staff.hs.0", "bb.staff.hs.1", "bb.staff.hs.2"};
		setUnlocalizedName("staffHungryShadows");
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
		if(!playerIn.worldObj.isRemote) {
			if(getActivePower(stack) == 0) {
				target.addPotionEffect(new PotionEffect(18, 2400));
				stack.damageItem(12, playerIn);
				return true;
			} else if(getActivePower(stack) == 1) {
				target.attackEntityFrom(DamageSource.magic, 4f);
				playerIn.heal(4f);
				stack.damageItem(12, playerIn);
				return true;
			} else if(getActivePower(stack) == 2 && target instanceof EntityPlayer) {
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
