package palaster.bb.entities.careers;

import net.minecraft.entity.player.EntityPlayer;
import palaster.bb.api.rpg.RPGCareerBase;

public class CareerGod extends RPGCareerBase {

	public CareerGod(EntityPlayer player) {
		if(!player.capabilities.allowFlying) {
			player.capabilities.allowFlying = true;
			player.sendPlayerAbilities();
		}
	}
	
	@Override
	public void leaveCareer(EntityPlayer player) {
		if(!player.isSpectator() && !player.capabilities.isCreativeMode) {
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
			player.capabilities.disableDamage = false;
			player.sendPlayerAbilities();
		}
	}
	
	@Override
	public String getUnlocalizedName() { return "bb.career.god"; }
}
