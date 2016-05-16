package palaster.bb.entities.effects;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class BBPotions {

    public static Potion timedFlame;

    public static void init() {
        timedFlame = (new BBPotion(true, 0xE25822)).setPotionName("effect.timedFlame");
    }
}
