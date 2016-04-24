package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;

public class UndeadCapabilityDefault implements IUndead {

    private boolean isUndead;
    private int souls;
    private int vigor;
    private int attunement;
    private int endurance;
    private int vitality;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int faith;
    private int luck;

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
    }

    @Override
    public int getEndurance() {
        if(isUndead)
            return endurance;
        return 0;
    }

    @Override
    public void setEndurance(int amt) {
        if(amt <= 0)
            endurance = 0;
        else
            endurance = amt;
    }

    @Override
    public int getVitality() {
        if(isUndead)
            return vitality;
        return 0;
    }

    @Override
    public void setVitality(int amt) {
        if(amt <= 0)
            vitality = 0;
        else
            vitality = amt;
    }

    @Override
    public int getStrength() { return strength; }

    @Override
    public void setStrength(int amt) {
        if(amt <= 0)
            strength = 0;
        else
            strength = amt;
    }

    @Override
    public int getDexterity() {
        if(isUndead)
            return dexterity;
        return 0;
    }

    @Override
    public void setDexterity(int amt) {
        if(amt <= 0)
            dexterity = 0;
        else
            dexterity = amt;
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
    }

    @Override
    public int getLuck() {
        if(isUndead)
            return luck;
        return 0;
    }

    @Override
    public void setLuck(int amt) {
        if(amt <= 0)
            luck = 0;
        else
            luck = amt;
    }

    @Override
    public NBTTagCompound saveNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setBoolean("IsUndead", isUndead);
        tagCompound.setInteger("Souls", souls);
        tagCompound.setInteger("Vigor", vigor);
        tagCompound.setInteger("Attunement", attunement);
        tagCompound.setInteger("Endurance", endurance);
        tagCompound.setInteger("Vitality", vitality);
        tagCompound.setInteger("Strength", strength);
        tagCompound.setInteger("Dexterity", dexterity);
        tagCompound.setInteger("Intelligence", intelligence);
        tagCompound.setInteger("Faith", faith);
        tagCompound.setInteger("Luck", luck);
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        isUndead = nbt.getBoolean("IsUndead");
        souls = nbt.getInteger("Souls");
        vigor = nbt.getInteger("Vigor");
        attunement = nbt.getInteger("Attunement");
        endurance = nbt.getInteger("Endurance");
        vitality = nbt.getInteger("Vitality");
        strength = nbt.getInteger("Strength");
        dexterity= nbt.getInteger("Dexterity");
        intelligence = nbt.getInteger("Intelligence");
        faith = nbt.getInteger("Faith");
        luck = nbt.getInteger("Luck");
    }
}
