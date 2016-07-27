package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.api.rpg.IRPGCareer;

public interface IRPG {
	
	IRPGCareer getCareer();
	
	void setCareer(IRPGCareer career);
	
	NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
