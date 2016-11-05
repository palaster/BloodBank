package palaster.bb.entities.effects;

import net.minecraft.potion.Potion;

public class BBPotions {

    public static Potion timedFlame,
    bloodHive,
    instantDeath,
    curseUnblinkingEye,
    curseBlackTongue,
    darkWings,
    peace;

    public static void init() {
        timedFlame = new PotionMod("timedFlame", true, 0xE25822, 0);
        bloodHive = new PotionMod("bloodHive", true, 0x8A0707, 1);
        instantDeath = new PotionMod("instantDeath", true, 0x000000, 2);
        curseUnblinkingEye = new PotionMod("curseUnblinkingEye", true, 0x551A8B, 3);
        curseBlackTongue = new PotionMod("curseBlackTongue", true, 0x551A8B, 4);
        darkWings = new PotionModDarkWings("darkWings", false, 0x551A8B, 5);
        peace = new PotionMod("peace", false, 0x00ff00, 6);
    }
}
