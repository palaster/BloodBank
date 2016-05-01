package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;

public interface IUndead {

    // TODO: Finish adding modifiers and add magic.

    boolean isUndead();

    void setUndead(boolean bool);

    int getSoul();

    void setSoul(int amt);

    int getVigor();

    void setVigor(int amt);

    int getAttunement();

    void setAttunement(int amt);

    int getStrength();

    void setStrength(int amt);

    int getIntelligence();

    void setIntelligence(int amt);

    int getFaith();

    void setFaith(int amt);

    NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
