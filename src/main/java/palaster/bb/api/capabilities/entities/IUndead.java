package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;

public interface IUndead {

    boolean isUndead();

    void setUndead(boolean bool);

    int getSoul();
    
    void addSoul(int amt);

    void setSoul(int amt);

    int getFocus();
    
    void addFocus(int amt);
    
    void useFocus(int amt);

    void setFocus(int amt);

    int getFocusMax();

    void setFocusMax(int amt);

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
