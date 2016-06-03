package palaster.bb.api.capabilities.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class UndeadCapabilityProvider implements ICapabilityProvider, INBTSerializable {

    @CapabilityInject(IUndead.class)
    public static final Capability<IUndead> undeadCap = null;

    protected IUndead undead = null;

    public UndeadCapabilityProvider() { undead = new UndeadCapabilityDefault(); }

    public static IUndead get(EntityPlayer player) {
        if(player.hasCapability(undeadCap, null))
            return player.getCapability(undeadCap, null);
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return undeadCap != null && capability == undeadCap; }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(undeadCap != null && capability == undeadCap)
            return (T) undead;
        return null;
    }

    @Override
    public NBTBase serializeNBT() { return undead.saveNBT(); }

    @Override
    public void deserializeNBT(NBTBase nbt) { undead.loadNBT((NBTTagCompound) nbt); }
}
