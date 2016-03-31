package palaster.bb.capabilities.entities;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import palaster.bb.BloodBank;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.client.UpdateCapabilitiesMessage;

import java.util.concurrent.Callable;

public class BloodBankCapability {

    public interface IBloodBank {

        void consumeBlood(EntityPlayer player, int amt);

        int getCurrentBlood();

        void addBlood(int amt);

        void setCurrentBlood(int amt);

        int getBloodMax();

        void setBloodMax(int amt);

        void linkEntity(EntityLiving entityLiving);

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
                if(tagCompound.getCompoundTag("LinkEntity") != null)
                    instance.linkEntity((EntityLiving) EntityList.createEntityFromNBT(tagCompound.getCompoundTag("LinkEntity"), DimensionManager.getWorld(0)));
            }
        }
    }

    public static class Factory implements Callable<IBloodBank> {
        @Override
        public IBloodBank call() throws Exception { return new DefaultImpl(); }
    }

    public static class DefaultImpl implements IBloodBank {

        // TODO: It not saving between world exits, maybe needs sync.

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
    }
}