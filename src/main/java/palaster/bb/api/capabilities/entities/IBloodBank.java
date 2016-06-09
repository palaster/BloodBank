package palaster.bb.api.capabilities.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

public interface IBloodBank {

    int getCurrentBlood();

    void setCurrentBlood(int amt);

    int getBloodMax();

    void setBloodMax(int amt);
    
    boolean isLinked();

    void linkEntity(EntityLiving entityLiving);

    EntityLiving getLinked();

    NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
