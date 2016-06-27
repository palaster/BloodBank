package palaster.bb.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import palaster.bb.libs.LibNBT;

public class BBWorldSaveData extends WorldSavedData {

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
        for(int i = 0; i < nbt.getInteger("BonFireAmt"); i++)
            bonFirePos.add(new BlockPos(nbt.getInteger("BonFireX" + i), nbt.getInteger("BonFireY" + i), nbt.getInteger("BonFireZ" + i)));
        for(int i = 0; i < nbt.getInteger(LibNBT.number); i++)
        	addDeadEntity(nbt.getCompoundTag(LibNBT.tag + i));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        int i = 0;
        for(BlockPos bp : bonFirePos)
            if(bp != null) {
                nbt.setInteger("BonFireX" + i, bp.getX());
                nbt.setInteger("BonFireY" + i, bp.getY());
                nbt.setInteger("BonFireZ" + i, bp.getZ());
                i++;
            }
        nbt.setInteger("BonFireAmt", i);
        int j = 0;
        for(NBTTagCompound tag : deadEntities)
        	if(tag != null) {
        		nbt.setTag(LibNBT.tag + j, tag);
        		j++;
        	} 
        nbt.setInteger(LibNBT.number, j);
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
