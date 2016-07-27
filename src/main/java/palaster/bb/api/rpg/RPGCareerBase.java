package palaster.bb.api.rpg;

import net.minecraft.nbt.NBTTagCompound;

public class RPGCareerBase implements IRPGCareer {

	@Override
	public NBTTagCompound saveNBT() { return new NBTTagCompound(); }

	@Override
	public void loadNBT(NBTTagCompound nbt) {}	
}
