package palaster.bb.entities.effects;

import net.minecraft.potion.Potion;

public class BBPotions {

    public static Potion timedFlame;
    public static Potion bloodHive;
    public static Potion sandBody;
    public static Potion instantDeath;
    public static Potion curseUnblinkingEye;
    public static Potion curseBlackTongue;
    public static Potion darkWings;

    public static void init() {
        timedFlame = new PotionMod("timedFlame", true, 0xE25822, 0);
        bloodHive = new PotionMod("bloodHive", true, 0x8A0707, 1);
        sandBody = new PotionMod("sandBody", false, 0xC2B280, 2);
        instantDeath = new PotionMod("instantDeath", true, 0x000000, 3);
        curseUnblinkingEye = new PotionMod("curseUnblinkingEye", true, 0x551A8B, 4);
        curseBlackTongue = new PotionMod("curseBlackTongue", true, 0x551A8B, 5);
        darkWings = new PotionModDarkWings("darkWings", false, 0x551A8B, 6);
    }
}
