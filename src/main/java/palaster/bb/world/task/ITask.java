package palaster.bb.world.task;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface ITask {
	
	void onTick(World world);
	
	boolean isFinished();

	NBTTagCompound saveNBT(NBTTagCompound nbt);

    void loadNBT(NBTTagCompound nbt);
}
