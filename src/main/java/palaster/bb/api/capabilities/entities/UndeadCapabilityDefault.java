package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;

public class UndeadCapabilityDefault implements IUndead {

    private static final int maxLevel = 99;

    // Find out what the difference between miracles (Talismans or Sacred Chimes), sorceries (Staves), and pyromancies (Pyromancy Flame)

    private boolean isUndead;
    private int souls;
    private int vigor;
    private int attunement; // FP Amount and amount of attunements that can be carried at the same time
    private int strength; // Amount of damage dealt
    private int intelligence; // Reduces magic damage, increase spell potency, increases sorcery, pyromancy, and dark miracle(?)
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
        else
            attunement = amt;
        if(amt >= maxLevel)
            attunement = maxLevel;
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
        vigor = nbt.getInteger("Vigor");
        attunement = nbt.getInteger("Attunement");
        strength = nbt.getInteger("Strength");
        intelligence = nbt.getInteger("Intelligence");
        faith = nbt.getInteger("Faith");
    }
}
