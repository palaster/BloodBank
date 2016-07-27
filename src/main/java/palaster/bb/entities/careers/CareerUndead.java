package palaster.bb.entities.careers;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.api.rpg.RPGCareerBase;

public class CareerUndead extends RPGCareerBase {
	
    public static String tag_souls = "Souls";
    public static String tag_focus = "Focus";
    public static String tag_focusMax = "FocusMax";
    public static String tag_vigor = "Vigor";
    public static String tag_attunement = "Attunement";
    public static String tag_strength = "Strength";
    public static String tag_intelligence = "Intelligence";
    public static String tag_faith = "Faith";
	
	private static final int maxLevel = 99;

    public static final UUID healthID = UUID.fromString("246c351b-566e-401d-bd32-d2acbac366d4");
    public static final UUID strengthID = UUID.fromString("9e804b09-8713-4186-a5ae-09380c674204");

    private int souls;
    private int focus;
    private int focusMax;
    private int vigor;
    private int attunement;
    private int strength;
    private int intelligence; // Increase spell potency, increases sorcery, pyromancy, and dark miracle(?)
    private int faith; // Increases Miracles(?), increase dark miracles

    public int getSoul() { return souls; }
    
    public void addSoul(int amt) { setSoul(getSoul() + amt); }

    public void setSoul(int amt) {
        if(amt <= 0)
            souls = 0;
        else
            souls = amt;
    }

    public int getFocus() { return focus; }
    
    public void addFocus(int amt) { setFocus(getFocus() + amt); }
    
    public void useFocus(int amt) { setFocus(getFocus() - amt); }

    public void setFocus(int amt) {
        if(amt <= 0)
            focus = 0;
        else
            focus = amt;
        if(amt >= focusMax)
            focus = focusMax;
    }

    public int getFocusMax() { return focusMax; }

    public void setFocusMax(int amt) {
        if(amt <= 0)
            focusMax = 0;
        else
            focusMax = amt;
    }

    public int getVigor() { return vigor; }

    public void setVigor(int amt) {
        if(amt <= 0)
            vigor = 0;
        else
            vigor = amt;
        if(amt >= maxLevel)
            vigor = maxLevel;
    }

    public int getAttunement() { return attunement; }

    public void setAttunement(int amt) {
        if(amt <= 0)
            attunement = 0;
        else {
            attunement = amt;
            setFocusMax(100 + (10 * attunement));
        }
        if(amt >= maxLevel) {
            attunement = maxLevel;
            setFocusMax(100 + (10 * maxLevel));
        }
    }

    public int getStrength() { return strength; }

    public void setStrength(int amt) {
        if(amt <= 0)
            strength = 0;
        else
            strength = amt;
        if(amt >= maxLevel)
            strength = maxLevel;
    }

    public int getIntelligence() { return intelligence; }

    public void setIntelligence(int amt) {
        if(amt <= 0)
            intelligence = 0;
        else
            intelligence = amt;
        if(amt >= maxLevel)
            intelligence = maxLevel;
    }

    public int getFaith() { return faith; }

    public void setFaith(int amt) {
        if(amt <= 0)
            faith = 0;
        else
            faith = amt;
        if(amt >= maxLevel)
            faith = maxLevel;
    }

    @Override
    public NBTTagCompound saveNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(tag_souls, souls);
        tagCompound.setInteger(tag_focus, focus);
        tagCompound.setInteger(tag_focusMax, focusMax);
        tagCompound.setInteger(tag_vigor, vigor);
        tagCompound.setInteger(tag_attunement, attunement);
        tagCompound.setInteger(tag_strength, strength);
        tagCompound.setInteger(tag_intelligence, intelligence);
        tagCompound.setInteger(tag_faith, faith);
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        souls = nbt.getInteger(tag_souls);
        focus = nbt.getInteger(tag_focus);
        focusMax = nbt.getInteger(tag_focusMax);
        vigor = nbt.getInteger(tag_vigor);
        attunement = nbt.getInteger(tag_attunement);
        strength = nbt.getInteger(tag_strength);
        intelligence = nbt.getInteger(tag_intelligence);
        faith = nbt.getInteger(tag_faith);
    }
}
