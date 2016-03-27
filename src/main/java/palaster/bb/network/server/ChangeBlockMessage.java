package palaster.bb.network.server;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.network.AbstractMessage.AbstractServerMessage;

import java.io.IOException;

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
