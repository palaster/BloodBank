package palaster.bb.api.capabilities.entities;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import palaster.bb.BloodBank;
import palaster.bb.libs.LibNBT;

public class BloodBankCapabilityDefault implements IBloodBank {

    private int bloodMax;
    private int bloodCurrent;
    private EntityLiving link = null;

    @Override
    public void consumeBlood(EntityPlayer player, int amt) {
        if(amt > getCurrentBlood())
            player.attackEntityFrom(BloodBank.proxy.bbBlood, (float) amt / 100);
        else
            setCurrentBlood(getCurrentBlood() - amt);
    }

    @Override
    public int getCurrentBlood() { return bloodCurrent; }

    @Override
    public void addBlood(int amt) {
        if((getCurrentBlood() + amt) >= bloodMax)
            setCurrentBlood(bloodMax);
        else
            setCurrentBlood(getCurrentBlood() + amt);
    }

    @Override
    public void setCurrentBlood(int amt) {
        if(amt >= bloodMax)
            bloodCurrent = bloodMax;
        else
            bloodCurrent = amt;
    }

    @Override
    public int getBloodMax() { return bloodMax; }

    @Override
    public void setBloodMax(int amt) { bloodMax = (amt > 0 ? amt : 0); }

    @Override
    public void linkEntity(EntityLiving entityLiving) {
        if(entityLiving == null)
            link = null;
        if(link == null)
            link = entityLiving;
    }

    @Override
    public EntityLiving getLinked() { return link; }

    @Override
    public NBTTagCompound saveNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(LibNBT.currentBlood, bloodCurrent);
        tagCompound.setInteger(LibNBT.maxBlood, bloodMax);
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        if(link != null) {
            link.writeToNBTAtomically(nbtTagCompound);
            tagCompound.setTag(LibNBT.linkEntity, nbtTagCompound);
        }
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        if(nbt != null) {
            bloodCurrent = nbt.getInteger(LibNBT.currentBlood);
            bloodMax = nbt.getInteger(LibNBT.maxBlood);
            if(nbt.getCompoundTag(LibNBT.linkEntity) != null)
                link = (EntityLiving) EntityList.createEntityFromNBT(nbt.getCompoundTag(LibNBT.linkEntity), DimensionManager.getWorld(0));
        }
    }
}
