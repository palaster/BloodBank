package palaster97.ss.network.server;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;

public class ChangeBlockMessage extends AbstractServerMessage<ChangeBlockMessage> {
	
	private BlockPos pos;
	private Block block;
	
	public ChangeBlockMessage() {}
	
	public ChangeBlockMessage(BlockPos pos, Block block) {
		this.pos = pos;
		this.block = block;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		pos = buffer.readBlockPos();
		block = Block.getBlockById(buffer.readInt());
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		if(pos != null)
			buffer.writeBlockPos(pos);
		buffer.writeInt(Block.getIdFromBlock(block));
	}

	@Override
	public void process(EntityPlayer player, Side side) { player.worldObj.setBlockState(pos, block.getDefaultState()); }
}
