package palaster.bb.entities.effects;

import net.minecraft.potion.Potion;

public class BBPotions {

    public static Potion timedFlame;
    public static Potion bloodHive;
    public static Potion sandBody;
    public static Potion instantDeath;

    public static void init() {
        timedFlame = new BBPotion("timedFlame", true, 0xE25822, 0);
        bloodHive = new BBPotion("bloodHive", true, 0x8A0707, 1);
        sandBody = new BBPotion("sandBody", false, 0xC2B280, 2);
        instantDeath = new BBPotion("instantDeath", true, 0x000000, 3);
    }
}
