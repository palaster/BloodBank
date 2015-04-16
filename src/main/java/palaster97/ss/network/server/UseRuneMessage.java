package palaster97.ss.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;

public class UseRuneMessage extends AbstractServerMessage<UseRuneMessage> {

	public UseRuneMessage() {}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.getCurrentEquippedItem() == null)
			if(SoulNetworkExtendedPlayer.get(player) != null)
				if(SoulNetworkExtendedPlayer.get(player).getRune() != null)
					SoulNetworkExtendedPlayer.get(player).getRune().activate(player.worldObj, player.getPosition(), player);
	}
}
