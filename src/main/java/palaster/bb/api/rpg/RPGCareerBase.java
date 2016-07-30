package palaster.bb.api.rpg;

import net.minecraft.nbt.NBTTagCompound;

public abstract class RPGCareerBase implements IRPGCareer {
	
	@Override
	public String getUnlocalizedName() { return "bb.career.base"; }

	@Override
	public NBTTagCompound saveNBT() { return new NBTTagCompound(); }

	@Override
	public void loadNBT(NBTTagCompound nbt) {}	
}
