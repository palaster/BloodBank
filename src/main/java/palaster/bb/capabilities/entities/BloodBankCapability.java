package palaster.bb.capabilities.entities;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import palaster.bb.BloodBank;

public class BloodBankCapability {

    @CapabilityInject(IBloodBank.class)
    public static final Capability<IBloodBank> bloodBankCap = null;

    public interface IBloodBank {

        void consumeBlood(int amt);

        int getCurrentBlood();

        void addBlood(int amt);

        void setCurrentBlood(int amt);

        int getBloodMax ();

        void setBloodMax(int amt);

        void linkEntity(EntityLiving entityLiving);

        EntityPlayer getPlayer();

        EntityLiving getLinked();
    }

    public static class Storage implements Capability.IStorage<IBloodBank> {
        @Override
        public NBTBase writeNBT(Capability<IBloodBank> capability, IBloodBank instance, EnumFacing side) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("CurrentBlood", instance.getCurrentBlood());
            tagCompound.setInteger("MaxBlood", instance.getBloodMax());
            if(instance.getLinked() != null)
                tagCompound.setTag("LinkEntity", instance.getLinked().getEntityData());
            return tagCompound;
        }

        @Override
        public void readNBT(Capability<IBloodBank> capability, IBloodBank instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound tagCompound = (NBTTagCompound) nbt;
            if(tagCompound != null) {
                instance.setCurrentBlood(tagCompound.getInteger("CurrentBlood"));
                instance.setBloodMax(tagCompound.getInteger("MaxBlood"));
                if(tagCompound.getCompoundTag("LinkEntity") != null && instance.getPlayer() != null)
                    if(EntityList.createEntityFromNBT(tagCompound.getCompoundTag("LinkEntity"), instance.getPlayer().worldObj) != null)
                        instance.linkEntity((EntityLiving) EntityList.createEntityFromNBT(tagCompound.getCompoundTag("LinkEntity"), instance.getPlayer().worldObj));
            }
        }
    }

    public static class DefaultImpl implements IBloodBank {

        // TODO: Get a instance of the player connected to.
        private EntityPlayer player;
        private int bloodMax = 0;
        private int bloodCurrent = 0;
        private EntityLiving link = null;

        @Override
        public void consumeBlood(int amt) {
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
        public EntityPlayer getPlayer() { return player; }
    }
}
