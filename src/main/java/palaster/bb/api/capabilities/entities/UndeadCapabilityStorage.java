package palaster.bb.api.capabilities.entities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class UndeadCapabilityStorage implements Capability.IStorage<IUndead> {

    @Override
    public NBTBase writeNBT(Capability<IUndead> capability, IUndead instance, EnumFacing side) { return instance.saveNBT(); }

    @Override
    public void readNBT(Capability<IUndead> capability, IUndead instance, EnumFacing side, NBTBase nbt) { instance.loadNBT((NBTTagCompound) nbt); }
}
