package palaster97.ss.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;

public class OpenGuiMessage extends AbstractServerMessage<OpenGuiMessage> {

	private int id;
	private BlockPos pos;
	
	public OpenGuiMessage() {}
	
	public OpenGuiMessage(int id, BlockPos pos) {
		this.id = id;
		this.pos = pos;
	}
	
	@Override
	protected void read(PacketBuffer buffer) {
		id = buffer.readInt();
		pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) {
		buffer.writeInt(id);
		if(pos != null)
			buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) { player.openGui(ScreamingSouls.instance, this.id, player.worldObj, pos.getX(), pos.getY(), pos.getZ()); }
}
