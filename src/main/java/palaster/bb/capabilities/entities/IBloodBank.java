package palaster.bb.capabilities.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IBloodBank {

    void consumeBlood(EntityPlayer player, int amt);

    int getCurrentBlood();

    void addBlood(int amt);

    void setCurrentBlood(int amt);

    int getBloodMax();

    void setBloodMax(int amt);

    void linkEntity(EntityLiving entityLiving);

    EntityLiving getLinked();

    NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
