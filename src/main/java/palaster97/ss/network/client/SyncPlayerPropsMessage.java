package palaster97.ss.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.entities.extended.SSExtendedPlayer;
import palaster97.ss.network.AbstractMessage.AbstractClientMessage;

public class SyncPlayerPropsMessage extends AbstractClientMessage<SyncPlayerPropsMessage> {
	
	private NBTTagCompound data;

	public SyncPlayerPropsMessage() {}

	public SyncPlayerPropsMessage(EntityPlayer player) {
		data = new NBTTagCompound();
		SSExtendedPlayer.get(player).saveNBTData(data);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException { data = buffer.readNBTTagCompoundFromBuffer(); }

	@Override
	protected void write(PacketBuffer buffer) throws IOException { buffer.writeNBTTagCompoundToBuffer(data); }

	@Override
	public void process(EntityPlayer player, Side side) { SSExtendedPlayer.get(player).loadNBTData(data); }
}
