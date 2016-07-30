package palaster.bb.api.rpg;

import net.minecraft.nbt.NBTTagCompound;

public interface IRPGCareer {
	
	String getUnlocalizedName();
	
	NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
