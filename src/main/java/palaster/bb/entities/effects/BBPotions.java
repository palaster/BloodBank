package palaster.bb.entities.effects;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import palaster.bb.libs.LibMod;

public class BBPotions {

    public static Potion timedFlame;

    public static void init() {
        timedFlame = (new BBPotion("timedFlame", true, 0xE25822, 0));
    }
}
