package palaster.bb.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.ArrayList;
import java.util.List;

public class BBWorldSaveData extends WorldSavedData {

    private static final String IDENTIFIER = "BloodBankWorldSaveData";
    private List<BlockPos> bonFirePos = new ArrayList<BlockPos>();

    public BBWorldSaveData() {
        super(IDENTIFIER);
    }

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

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        int j = nbt.getInteger("BonFireAmt");
        for(int i = 0; i < j; i++)
            bonFirePos.add(new BlockPos(nbt.getInteger("BonFireX" + i), nbt.getInteger("BonFireY" + i), nbt.getInteger("BonFireZ" + i)));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        int i = 0;
        for(BlockPos bp : bonFirePos) {
            if(bp != null) {
                nbt.setInteger("BonFireX" + i, bp.getX());
                nbt.setInteger("BonFireY" + i, bp.getY());
                nbt.setInteger("BonFireZ" + i, bp.getZ());
            }
            i++;
        }
        nbt.setInteger("BonFireAmt", i);
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
