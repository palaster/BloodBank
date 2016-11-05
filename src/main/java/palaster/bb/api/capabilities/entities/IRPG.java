package palaster.bb.api.capabilities.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.api.rpg.RPGCareerBase;

public interface IRPG {
	
	public void setConstitution(EntityPlayer player, int amt);
	
	public int getConstitution();
	
	public void setStrength(EntityPlayer player, int amt);
	
	public int getStrength();
	
	public void setDefense(int amt);
	
	public int getDefense();
	
	public void setDexterity(EntityPlayer player, int amt);
	
	public int getDexterity();
	
	public void setIntelligence(int amt);
	
	public int getIntelligence();
	
	public void setMagick(int amt);
	
	public int getMagick();
	
	public int getMaxMagick();
	
	void setCareer(EntityPlayer player, RPGCareerBase career);
	
	RPGCareerBase getCareer();
	
	NBTTagCompound saveNBT();

    void loadNBT(EntityPlayer player, NBTTagCompound nbt);
}
