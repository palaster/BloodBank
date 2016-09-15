package palaster.bb.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import palaster.bb.world.task.ITask;

public class BBWorldSaveData extends WorldSavedData {
	
	public static final String TAG_INT_BONFIRE_POS = "Bonfire";
	public static final String TAG_INT_BONFIRE = "BonfireNumber";
	public static final String TAG_TAG_DEAD = "DeadEntityTag";
	public static final String TAG_INT_DEAD = "DeadEntityNumber";
	public static final String TAG_INT_TASK = "TaskNumber";
	public static final String TAG_STRING_TASK = "TaskClass";
	public static final String TAG_TAG_TASK = "TaskTag";
	
    private static final String IDENTIFIER = "BloodBankWorldSaveData";
    
    private List<BlockPos> bonFirePos = new ArrayList<BlockPos>();
    private List<NBTTagCompound> deadEntities = new ArrayList<NBTTagCompound>();
    private List<ITask> worldTask = new ArrayList<ITask>();

    public BBWorldSaveData() { super(IDENTIFIER); }

    public BBWorldSaveData(String identity) { super(identity); }
    
    public void addBonfire(BlockPos pos) {
        bonFirePos.add(pos);
        markDirty();
    }

    public void removeBonfire(BlockPos pos) {
        bonFirePos.remove(pos);
        markDirty();
    }

    public BlockPos getNearestBonfireToPlayer(EntityPlayer player, BlockPos current) {
        BlockPos nearest = new BlockPos(player.worldObj.getSpawnPoint());
        for(BlockPos pos: bonFirePos) {
            if(pos != null)
                if(pos.getDistance(current.getX(), current.getY(), current.getZ()) < nearest.getDistance(current.getX(), current.getY(), current.getZ()))
                    nearest = pos;
        }
        return nearest;
    }
    
    public List<NBTTagCompound> getDeadEntities() { return deadEntities; }
    
    public NBTTagCompound getDeadEntity(int numb) { return deadEntities.get(numb); }
    
    public void addDeadEntity(NBTTagCompound nbt) {
    	deadEntities.add(nbt);
    	markDirty();
    }
    
    public void removeDeadEntity(NBTTagCompound nbt) {
    	if(deadEntities.contains(nbt))
    		deadEntities.remove(nbt);
    	markDirty();
    }
    
    public void clearDeadEntities(World world) { deadEntities.clear(); }
    
    public void addTask(ITask task) {
    	worldTask.add(task);
    	markDirty();
    }
    
    public void removeTask(ITask task) {
    	if(worldTask.contains(task))
    		worldTask.remove(task);
    	markDirty();
    }
    
    public void tickTask(World world) {
    	for(int i = 0; i < worldTask.size(); i++)
    		if(worldTask.get(i) != null) {
    			worldTask.get(i).onTick(world);
    			if(worldTask.get(i).isFinished())
    				removeTask(worldTask.get(i));
    		}
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for(int i = 0; i < nbt.getInteger(TAG_INT_BONFIRE); i++)
            bonFirePos.add(new BlockPos(nbt.getInteger(TAG_INT_BONFIRE_POS + "X" + i), nbt.getInteger(TAG_INT_BONFIRE_POS + "Y" + i), nbt.getInteger(TAG_INT_BONFIRE_POS + "Z" + i)));
        for(int i = 0; i < nbt.getInteger(TAG_INT_DEAD); i++)
        	addDeadEntity(nbt.getCompoundTag(TAG_TAG_DEAD + i));
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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        int i = 0;
        if(!bonFirePos.isEmpty())
	        for(BlockPos bp : bonFirePos)
	            if(bp != null) {
	                nbt.setInteger(TAG_INT_BONFIRE_POS + "X" + i, bp.getX());
	                nbt.setInteger(TAG_INT_BONFIRE_POS + "Y" + i, bp.getY());
	                nbt.setInteger(TAG_INT_BONFIRE_POS + "Z" + i, bp.getZ());
	                i++;
	            }
        nbt.setInteger(TAG_INT_BONFIRE, i);
        int j = 0;
        if(!deadEntities.isEmpty())
	        for(NBTTagCompound tag : deadEntities)
	        	if(tag != null) {
	        		nbt.setTag(TAG_TAG_DEAD + j, tag);
	        		j++;
	        	}
        nbt.setInteger(TAG_INT_DEAD, j);
        int k = 0;
        if(!worldTask.isEmpty())
        	for(ITask task : worldTask)
	        	if(task != null) {
	        		nbt.setTag(TAG_TAG_TASK + k, task.saveNBT(new NBTTagCompound()));
	        		nbt.setString(TAG_STRING_TASK + k, task.getClass().getName());
	        		k++;
	        	}
        nbt.setInteger(TAG_INT_TASK, k);
        return nbt;
    }

    public static BBWorldSaveData get(World world) {
        BBWorldSaveData data = (BBWorldSaveData)world.getPerWorldStorage().getOrLoadData(BBWorldSaveData.class, IDENTIFIER);
        if(data == null) {
            data = new BBWorldSaveData();
            world.getPerWorldStorage().setData(IDENTIFIER, data);
        }
        return data;
    }
}
