package palaster.bb.api.rpg;

import net.minecraft.nbt.NBTTagCompound;

public interface IRPGCareer {
	
	NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
