package palaster.bb.network.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapabilityProvider;
import palaster.bb.network.AbstractMessage;

import java.io.IOException;

public class SyncPlayerPropsMessage extends AbstractMessage.AbstractClientMessage<SyncPlayerPropsMessage> {

    private NBTTagCompound nbtTagCompound;

    public SyncPlayerPropsMessage() {}

    public SyncPlayerPropsMessage(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            nbtTagCompound = undead.saveNBT();
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException { nbtTagCompound = buffer.readNBTTagCompoundFromBuffer(); }

    @Override
    protected void write(PacketBuffer buffer) throws IOException { buffer.writeNBTTagCompoundToBuffer(nbtTagCompound); }

    @Override
    public void process(EntityPlayer player, Side side) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.loadNBT(nbtTagCompound);
    }
}
