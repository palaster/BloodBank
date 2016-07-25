package palaster.bb.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class BBWorldSaveData extends WorldSavedData {
	
	public static String tag_bonfire = "Bonfire";
	public static String tag_bonfireNumber = "BonfireNumber";
	public static String tag_entityTag = "DeadEntityTag";
	public static String tag_deadNumber = "DeadEntityNumber";

    private static final String IDENTIFIER = "BloodBankWorldSaveData";
    private List<BlockPos> bonFirePos = new ArrayList<BlockPos>();
    private List<NBTTagCompound> deadEntities = new ArrayList<NBTTagCompound>();

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
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for(int i = 0; i < nbt.getInteger(tag_bonfireNumber); i++)
            bonFirePos.add(new BlockPos(nbt.getInteger(tag_bonfire + "X" + i), nbt.getInteger(tag_bonfire + "Y" + i), nbt.getInteger(tag_bonfire + "Z" + i)));
        for(int i = 0; i < nbt.getInteger(tag_deadNumber); i++)
        	addDeadEntity(nbt.getCompoundTag(tag_entityTag + i));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        int i = 0;
        for(BlockPos bp : bonFirePos)
            if(bp != null) {
                nbt.setInteger(tag_bonfire + "X" + i, bp.getX());
                nbt.setInteger(tag_bonfire + "Y" + i, bp.getY());
                nbt.setInteger(tag_bonfire + "Z" + i, bp.getZ());
                i++;
            }
        nbt.setInteger(tag_bonfireNumber, i);
        int j = 0;
        for(NBTTagCompound tag : deadEntities)
        	if(tag != null) {
        		nbt.setTag(tag_entityTag + j, tag);
        		j++;
        	} 
        nbt.setInteger(tag_deadNumber, j);
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
