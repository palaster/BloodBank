package palaster.bb.api.capabilities.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface ISpecialArmorAbility {

	void doArmorAbility(World worldObj, EntityPlayer player);
}
