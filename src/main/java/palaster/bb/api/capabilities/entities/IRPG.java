package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.api.rpg.IRPGCareer;

public interface IRPG {
	
	public void setConstitution(int amt);
	
	public int getConstitution();
	
	public void setStrength(int amt);
	
	public int getStrength();
	
	public void setDefense(int amt);
	
	public int getDefense();
	
	public void setDexterity(int amt);
	
	public int getDexterity();
	
	void setCareer(IRPGCareer career);
	
	IRPGCareer getCareer();
	
	NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
