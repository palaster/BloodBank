package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.libs.LibNBT;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.client.SyncPlayerPropsMessage;

import java.util.UUID;

public class UndeadCapabilityDefault implements IUndead {

    private static final int maxLevel = 99;

    public static final UUID healthID = UUID.fromString("246c351b-566e-401d-bd32-d2acbac366d4");
    public static final UUID strengthID = UUID.fromString("9e804b09-8713-4186-a5ae-09380c674204");

    private boolean isUndead;
    private int souls;
    private int focus;
    private int focusMax;
    private int vigor;
    private int attunement;
    private int strength;
    private int intelligence; // Increase spell potency, increases sorcery, pyromancy, and dark miracle(?)
    private int faith; // Increases Miracles(?), increase dark miracles

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
        if(amt >= focusMax)
            focus = focusMax;
    }

    @Override
    public int getFocusMax() { return focusMax; }

    @Override
    public void setFocusMax(int amt) {
        if(amt <= 0)
            focusMax = 0;
        else
            focusMax = amt;
    }

    @Override
    public int getVigor() { return vigor; }

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
    public int getAttunement() { return attunement; }

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
    public int getIntelligence() { return intelligence; }

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
    public int getFaith() { return faith; }

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
        tagCompound.setBoolean(LibNBT.isUndead, isUndead);
        tagCompound.setInteger(LibNBT.souls, souls);
        tagCompound.setInteger(LibNBT.focus, focus);
        tagCompound.setInteger(LibNBT.focusMax, focusMax);
        tagCompound.setInteger(LibNBT.vigor, vigor);
        tagCompound.setInteger(LibNBT.attunement, attunement);
        tagCompound.setInteger(LibNBT.strength, strength);
        tagCompound.setInteger(LibNBT.intelligence, intelligence);
        tagCompound.setInteger(LibNBT.faith, faith);
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        isUndead = nbt.getBoolean(LibNBT.isUndead);
        souls = nbt.getInteger(LibNBT.souls);
        focus = nbt.getInteger(LibNBT.focus);
        focusMax = nbt.getInteger(LibNBT.focusMax);
        vigor = nbt.getInteger(LibNBT.vigor);
        attunement = nbt.getInteger(LibNBT.attunement);
        strength = nbt.getInteger(LibNBT.strength);
        intelligence = nbt.getInteger(LibNBT.intelligence);
        faith = nbt.getInteger(LibNBT.faith);
    }
}
