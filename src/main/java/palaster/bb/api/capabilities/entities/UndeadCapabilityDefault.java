package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class UndeadCapabilityDefault implements IUndead {

    private static final int maxLevel = 99;

    public static final UUID healthID = UUID.fromString("246c351b-566e-401d-bd32-d2acbac366d4");
    public static final UUID strengthID = UUID.fromString("9e804b09-8713-4186-a5ae-09380c674204");

    // Miracles - Talismans, Pyromancy - Pyromancy Flame, and Sorceries - Staff

    private boolean isUndead;
    private int souls;
    private int focus;
    private int focusMax;
    private int vigor;
    private int attunement;
    private int strength;
    private int intelligence; // Increase spell potency, increases sorcery, pyromancy, and dark miracle(?)
    private int faith; // Increases Miracles(?), increases pyromancy, increase dark miracles

    @Override
    public boolean isUndead() { return isUndead; }

    @Override
    public void setUndead(boolean bool) { isUndead = bool; }

    @Override
    public int getSoul() {
        if(isUndead)
            return souls;
        return 0;
    }

    @Override
    public void setSoul(int amt) {
        if(amt <= 0)
            souls = 0;
        else
            souls = amt;
    }

    @Override
    public int getFocus() {
        if(isUndead)
            return focus;
        return 0;
    }

    @Override
    public void setFocus(int amt) {
        if(amt <= 0)
            focus = 0;
        else
            focus = amt;
    }

    @Override
    public int getFocusMax() {
        if(isUndead)
            return focusMax;
        return focusMax;
    }

    @Override
    public void setFocusMax(int amt) {
        if(amt <= 0)
            focusMax = 0;
        else
            focusMax = amt;
    }

    @Override
    public int getVigor() {
        if(isUndead)
            return vigor;
        return 0;
    }

    @Override
    public void setVigor(int amt) {
        if(amt <= 0)
            vigor = 0;
        else
            vigor = amt;
        if(amt >= maxLevel)
            vigor = maxLevel;
    }

    @Override
    public int getAttunement() {
        if(isUndead)
            return attunement;
        return 0;
    }

    @Override
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

    @Override
    public int getStrength() { return strength; }

    @Override
    public void setStrength(int amt) {
        if(amt <= 0)
            strength = 0;
        else
            strength = amt;
        if(amt >= maxLevel)
            strength = maxLevel;
    }

    @Override
    public int getIntelligence() {
        if(isUndead)
            return intelligence;
        return 0;
    }

    @Override
    public void setIntelligence(int amt) {
        if(amt <= 0)
            intelligence = 0;
        else
            intelligence = amt;
        if(amt >= maxLevel)
            intelligence = maxLevel;
    }

    @Override
    public int getFaith() {
        if(isUndead)
            return faith;
        return 0;
    }

    @Override
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
        tagCompound.setBoolean("IsUndead", isUndead);
        tagCompound.setInteger("Souls", souls);
        tagCompound.setInteger("Focus", focus);
        tagCompound.setInteger("FocusMax", focusMax);
        tagCompound.setInteger("Vigor", vigor);
        tagCompound.setInteger("Attunement", attunement);
        tagCompound.setInteger("Strength", strength);
        tagCompound.setInteger("Intelligence", intelligence);
        tagCompound.setInteger("Faith", faith);
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        isUndead = nbt.getBoolean("IsUndead");
        souls = nbt.getInteger("Souls");
        focus = nbt.getInteger("Focus");
        focusMax = nbt.getInteger("FocusMax");
        vigor = nbt.getInteger("Vigor");
        attunement = nbt.getInteger("Attunement");
        strength = nbt.getInteger("Strength");
        intelligence = nbt.getInteger("Intelligence");
        faith = nbt.getInteger("Faith");
    }
}
