package palaster97.ss.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;

public class OpenGuiMessage extends AbstractServerMessage<OpenGuiMessage> {

	private int id;
	
	public OpenGuiMessage() {}
	
	public OpenGuiMessage(int id) { this.id = id; }
	
	@Override
	protected void read(PacketBuffer buffer) { id = buffer.readInt(); }

	@Override
	protected void write(PacketBuffer buffer) { buffer.writeInt(id); }

	@Override
	public void process(EntityPlayer player, Side side) { player.openGui(ScreamingSouls.instance, this.id, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ); }
}
