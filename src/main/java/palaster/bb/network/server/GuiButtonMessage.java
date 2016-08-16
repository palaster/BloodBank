package palaster.bb.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.capabilities.items.IRecieveButton;
import palaster.bb.blocks.tile.TileEntityMod;
import palaster.bb.network.AbstractMessage.AbstractServerMessage;

public class GuiButtonMessage extends AbstractServerMessage<GuiButtonMessage> {
	
	private String name;
	private BlockPos pos;
	private int id;

	public GuiButtonMessage() {}
	
	public GuiButtonMessage(String name, BlockPos pos, int id) {
		this.name = name;
		this.pos = pos;
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		int temp = buffer.readInt();
		if(temp > 0) {
			name = "";
			for(int i = 0; i < temp; i++)
				name += buffer.readChar();
		}
		pos = buffer.readBlockPos();
		id = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		if(name != null) {
			buffer.writeInt(name.length());
			for(int i = 0; i < name.length(); i++)
				buffer.writeChar(name.charAt(i));
		}
		if(pos != null)
			buffer.writeBlockPos(pos);
		buffer.writeInt(id);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.worldObj.getTileEntity(pos);
		if(te != null && te instanceof TileEntityMod)
			((TileEntityMod) te).receiveButtonEvent(id, player);
		if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof IRecieveButton)
			((IRecieveButton) player.getHeldItemMainhand().getItem()).receiveButtonEvent(id, player);
		if(player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof IRecieveButton)
			((IRecieveButton) player.getHeldItemOffhand().getItem()).receiveButtonEvent(id, player);
	}
}
