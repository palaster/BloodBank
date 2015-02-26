package palaster97.ss.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.blocks.tile.TileEntityPlayerStatue;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;

public class ChangeStringMessage extends AbstractServerMessage<ChangeStringMessage> {

	private BlockPos pos;
	private String name;
	
	public ChangeStringMessage() {}
	
	public ChangeStringMessage(BlockPos pos, String name) {
		this.pos = pos;
		this.name = name;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		pos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
		int temp = buffer.readInt();
		name = "";
		for(int i = 0 ; i < temp; i++)
			name += buffer.readChar();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(pos.getX());
		buffer.writeInt(pos.getY());
		buffer.writeInt(pos.getZ());
		buffer.writeInt(name.length());
		for(int i = 0; i < name.length(); i++)
			buffer.writeChar(name.charAt(i));
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntityPlayerStatue ps = (TileEntityPlayerStatue) player.worldObj.getTileEntity(pos);
		if(ps != null) {
			ps.setUUIDName(name);
			player.worldObj.markBlockForUpdate(pos);
		}
	}	
}
