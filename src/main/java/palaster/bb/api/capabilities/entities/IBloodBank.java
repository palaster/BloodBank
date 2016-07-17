package palaster.bb.api.capabilities.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

public interface IBloodBank {
	
	int consumeBlood(int amt);
	
	void addBlood(int amt);

    int getCurrentBlood();

    void setCurrentBlood(int amt);

    int getMaxBlood();

    void setMaxBlood(int amt);
    
    boolean isLinked();

    void linkEntity(EntityLiving entityLiving);

    EntityLiving getLinked();

    NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
