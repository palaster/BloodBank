package palaster.bb.capabilities.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class BloodBankCapabilityProvider implements ICapabilityProvider, INBTSerializable {

    @CapabilityInject(IBloodBank.class)
    public static final Capability<IBloodBank> bloodBankCap = null;

    protected IBloodBank bloodBank = null;

    public BloodBankCapabilityProvider() { bloodBank = new BloodBankCapabilityDefault(); }

    public static IBloodBank get(EntityPlayer player) {
        if(player.hasCapability(bloodBankCap, null))
            return player.getCapability(bloodBankCap, null);
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return bloodBankCap != null && capability == bloodBankCap; }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(bloodBankCap != null && capability == bloodBankCap)
            return (T) bloodBank;
        return null;
    }

    @Override
    public NBTBase serializeNBT() { return bloodBank.saveNBT(); }

    @Override
    public void deserializeNBT(NBTBase nbt) { bloodBank.loadNBT((NBTTagCompound) nbt); }
}
