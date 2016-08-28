package palaster.bb.world.task;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Task implements ITask {
	
	public static final String TAG_BOOLEAN_FINISHED = "IsFinished";
	
	private boolean isFinished;

	@Override
	public void onTick(World world) { isFinished = true; }
	
	@Override
	public boolean isFinished() { return isFinished; }

	@Override
	public NBTTagCompound saveNBT() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setBoolean(TAG_BOOLEAN_FINISHED, isFinished);
		return nbtTagCompound;
	}

	@Override
	public void loadNBT(NBTTagCompound nbt) { isFinished = nbt.getBoolean(TAG_BOOLEAN_FINISHED); }
}
