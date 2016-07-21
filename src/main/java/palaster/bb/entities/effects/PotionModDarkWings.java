package palaster.bb.entities.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;

public class PotionModDarkWings extends PotionMod {

	public PotionModDarkWings(String name, boolean isBadEffectIn, int liquidColorIn, int iconIndex) { super(name, isBadEffectIn, liquidColorIn, iconIndex); }
	
	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
		if(entityLivingBaseIn instanceof EntityPlayer)
			if(!((EntityPlayer) entityLivingBaseIn).isSpectator() && !((EntityPlayer) entityLivingBaseIn).capabilities.isCreativeMode) {
				((EntityPlayer) entityLivingBaseIn).capabilities.allowFlying = false;
				((EntityPlayer) entityLivingBaseIn).capabilities.isFlying = false;
				((EntityPlayer) entityLivingBaseIn).capabilities.disableDamage = false;
				((EntityPlayer) entityLivingBaseIn).sendPlayerAbilities();
			}
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
	}
}