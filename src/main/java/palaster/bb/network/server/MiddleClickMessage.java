package palaster.bb.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.entities.extended.BBExtendedPlayer;
import palaster.bb.items.ItemModStaff;
import palaster.bb.network.AbstractMessage.AbstractServerMessage;

import java.io.IOException;

public class MiddleClickMessage extends AbstractServerMessage<MiddleClickMessage> {

	public MiddleClickMessage() {}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(BBExtendedPlayer.get(player) != null)
			if(player.getCurrentEquippedItem() != null)
				if(player.getCurrentEquippedItem().getItem() instanceof ItemModStaff)
					if(ItemModStaff.getActiveMax(player.getCurrentEquippedItem()) != 0)
						if(ItemModStaff.getActivePower(player.getCurrentEquippedItem()) == (ItemModStaff.getActiveMax(player.getCurrentEquippedItem()) - 1))
							ItemModStaff.setActivePower(player.getCurrentEquippedItem(), 0);
						else
							ItemModStaff.setActivePower(player.getCurrentEquippedItem(), ItemModStaff.getActivePower(player.getCurrentEquippedItem()) + 1);
	}
}
