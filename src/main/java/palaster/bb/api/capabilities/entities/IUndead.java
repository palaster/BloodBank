package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;

public interface IUndead {

    // TODO: Add modifiers, respawn at bonfires and keep items, add magic.

    boolean isUndead();

    void setUndead(boolean bool);

    int getSoul();

    void setSoul(int amt);

    int getVigor();

    void setVigor(int amt);

    int getAttunement();

    void setAttunement(int amt);

    int getEndurance();

    void setEndurance(int amt);

    int getVitality();

    void setVitality(int amt);

    int getStrength();

    void setStrength(int amt);

    int getDexterity();

    void setDexterity(int amt);

    int getIntelligence();

    void setIntelligence(int amt);

    int getFaith();

    void setFaith(int amt);

    int getLuck();

    void setLuck(int amt);

    NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
