package palaster.bb.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.ArrayList;
import java.util.List;

public class BBWorldSaveData extends WorldSavedData {

    private List<BlockPos> bonFirePos = new ArrayList<BlockPos>();

    public BBWorldSaveData() {
        super("BloodBankWorldSaveData");
    }

    public void addBonfire(BlockPos pos) { bonFirePos.add(pos); }

    public void removeBonfire(BlockPos pos) { bonFirePos.remove(pos); }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        int j = nbt.getInteger("BonFireAmt");
        for(int i = 0; i < j; i++)
            bonFirePos.add(new BlockPos(nbt.getInteger("BonFireX" + i), nbt.getInteger("BonFireY" + i), nbt.getInteger("BonFireZ" + i)));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
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
    }

    public static BBWorldSaveData get(World world) {
        BBWorldSaveData data = (BBWorldSaveData)world.loadItemData(BBWorldSaveData.class, "BloodBankWorldSaveData");
        if(data == null) {
            data = new BBWorldSaveData();
            world.setItemData("BloodBankWorldSaveData", data);
        }
        return data;
    }
}
