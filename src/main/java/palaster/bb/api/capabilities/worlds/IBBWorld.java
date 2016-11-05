package palaster.bb.api.capabilities.worlds;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IBBWorld {
    
	public void addDeadEntity(NBTTagCompound nbt);
    
    public void removeDeadEntity(NBTTagCompound nbt);
    
    public void clearDeadEntities(World world);
	
    public List<NBTTagCompound> getDeadEntities();
    
    public NBTTagCompound getDeadEntity(int numb);

	NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
