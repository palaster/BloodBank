package palaster.bb.entities.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class BBPotion extends Potion {

    public BBPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_) {}
}
