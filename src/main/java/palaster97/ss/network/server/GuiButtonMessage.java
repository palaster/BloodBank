package palaster97.ss.network.server;

import java.io.IOException;

import palaster97.ss.blocks.tile.TileEntityConjuringTablet;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class GuiButtonMessage extends AbstractServerMessage<GuiButtonMessage> {
	
	private BlockPos pos;
	private int id;

	public GuiButtonMessage() {}
	
	public GuiButtonMessage(BlockPos pos, int id) {
		this.pos = pos;
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		pos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
		id = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(pos.getX());
		buffer.writeInt(pos.getY());
		buffer.writeInt(pos.getZ());
		buffer.writeInt(id);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(id == 0) {
			TileEntity te = player.worldObj.getTileEntity(pos);
			if(te != null && te instanceof TileEntityConjuringTablet)
				((TileEntityConjuringTablet) te).trySummon(player);
		}
	}
}
