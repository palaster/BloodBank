package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class BloodBankCapabilityStorage implements Capability.IStorage<IBloodBank> {

    @Override
    public NBTBase writeNBT(Capability<IBloodBank> capability, IBloodBank instance, EnumFacing side) { return instance.saveNBT(); }

    @Override
    public void readNBT(Capability<IBloodBank> capability, IBloodBank instance, EnumFacing side, NBTBase nbt) { instance.loadNBT((NBTTagCompound) nbt); }
}
