package palaster.bb.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.entities.extended.BBExtendedPlayer;
import palaster.bb.network.AbstractMessage.AbstractClientMessage;

public class SyncPlayerPropsMessage extends AbstractClientMessage<SyncPlayerPropsMessage> {
	
	private NBTTagCompound data;

	public SyncPlayerPropsMessage() {}

	public SyncPlayerPropsMessage(EntityPlayer player) {
		data = new NBTTagCompound();
		BBExtendedPlayer.get(player).saveNBTData(data);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException { data = buffer.readNBTTagCompoundFromBuffer(); }

	@Override
	protected void write(PacketBuffer buffer) throws IOException { buffer.writeNBTTagCompoundToBuffer(data); }

	@Override
	public void process(EntityPlayer player, Side side) { BBExtendedPlayer.get(player).loadNBTData(data); }
}
