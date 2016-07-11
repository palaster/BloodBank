package palaster.bb.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.capabilities.entities.BloodBankCapability.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapability.UndeadCapabilityProvider;
import palaster.bb.network.AbstractMessage;

public class SyncPlayerPropsMessage extends AbstractMessage.AbstractClientMessage<SyncPlayerPropsMessage> {

	private NBTTagCompound bloodNBTTagCompound;
    private NBTTagCompound undeadNBTTagCompound;

    public SyncPlayerPropsMessage() {}

    public SyncPlayerPropsMessage(EntityPlayer player) {
    	final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
    	if(bloodBank != null)
    		bloodNBTTagCompound = bloodBank.saveNBT();
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
        	undeadNBTTagCompound = undead.saveNBT();
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
    	bloodNBTTagCompound = buffer.readNBTTagCompoundFromBuffer();
    	undeadNBTTagCompound = buffer.readNBTTagCompoundFromBuffer();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
    	buffer.writeNBTTagCompoundToBuffer(bloodNBTTagCompound);
    	buffer.writeNBTTagCompoundToBuffer(undeadNBTTagCompound);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
    	final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
    	if(bloodBank != null)
    		bloodBank.loadNBT(bloodNBTTagCompound);
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.loadNBT(undeadNBTTagCompound);
    }
}
