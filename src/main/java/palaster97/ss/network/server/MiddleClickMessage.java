package palaster97.ss.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemModStaff;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;

public class MiddleClickMessage extends AbstractServerMessage<MiddleClickMessage> {

	public MiddleClickMessage() {}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(SoulNetworkExtendedPlayer.get(player) != null) {
			if(player.getCurrentEquippedItem() == null) {
				if(SoulNetworkExtendedPlayer.get(player) != null && SoulNetworkExtendedPlayer.get(player).getRuneCharge()) 
					if(SoulNetworkExtendedPlayer.get(player).getRune() != null) {
						SoulNetworkExtendedPlayer.get(player).getRune().activate(player.worldObj, player.getPosition(), player);
						SoulNetworkExtendedPlayer.get(player).setRuneCharge(false);
					}
			} else if(player.getCurrentEquippedItem().getItem() instanceof ItemModStaff)
				if(ItemModStaff.getActiveMax(player.getCurrentEquippedItem()) != 0)
					if(ItemModStaff.getActivePower(player.getCurrentEquippedItem()) == (ItemModStaff.getActiveMax(player.getCurrentEquippedItem()) - 1))
						ItemModStaff.setActivePower(player.getCurrentEquippedItem(), 0);
					else
						ItemModStaff.setActivePower(player.getCurrentEquippedItem(), ItemModStaff.getActivePower(player.getCurrentEquippedItem()) + 1);
		}
	}
}
