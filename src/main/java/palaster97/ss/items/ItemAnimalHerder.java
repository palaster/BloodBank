package palaster97.ss.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemAnimalHerder extends ItemModSpecial {

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
			}
		}
		return false;
	}
}
