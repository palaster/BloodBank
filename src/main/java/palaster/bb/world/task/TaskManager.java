package palaster.bb.world.task;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import palaster.bb.world.BBWorldSaveData;

public class TaskManager {

	public static final String TAG_INT_TASK = "TaskNumber";
	public static final String TAG_STRING_TASK = "TaskClass";
	public static final String TAG_TAG_TASK = "TaskTag";
	
	private List<ITask> worldTask = new ArrayList<ITask>();
	private WeakReference<BBWorldSaveData> worldSaveData;
	
	public TaskManager(World world) { worldSaveData = new WeakReference<BBWorldSaveData>(BBWorldSaveData.get(world)); }
	
	public void addTask(ITask task) {
    	worldTask.add(task);
    	if(worldSaveData != null && worldSaveData.get() != null)
    		worldSaveData.get().markDirty();
    }
    
    public void removeTask(ITask task) {
    	if(worldTask.contains(task))
    		worldTask.remove(task);
    	if(worldSaveData != null && worldSaveData.get() != null)
    		worldSaveData.get().markDirty();
    }
    
    public void tickTask(World world) {
    	for(int i = 0; i < worldTask.size(); i++)
    		if(worldTask.get(i) != null) {
    			worldTask.get(i).onTick(world);
    			if(worldTask.get(i).isFinished())
    				removeTask(worldTask.get(i));
    		}
    }
    
    public void readFromNBT(NBTTagCompound nbt) {
    	for(int i = 0; i < nbt.getInteger(TAG_INT_TASK); i++) {
        	if(!nbt.getString(TAG_STRING_TASK + i).isEmpty()) {
        		try {
					Object obj = Class.forName(nbt.getString(TAG_STRING_TASK + i)).newInstance();
					if(obj != null && obj instanceof ITask) {
						addTask((ITask) obj);
						worldTask.get(i).loadNBT(nbt.getCompoundTag(TAG_TAG_TASK + i));
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
        	}
        }
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    	int k = 0;
    	for(; k < worldTask.size(); k++) {
        	if(worldTask.get(k) != null) {
        		nbt.setTag(TAG_TAG_TASK + k, worldTask.get(k).saveNBT());
        		nbt.setString(TAG_STRING_TASK + k, worldTask.get(k).getClass().getName());
        	}
        }
        nbt.setInteger(TAG_INT_TASK, k);
        return nbt;
    }
}
