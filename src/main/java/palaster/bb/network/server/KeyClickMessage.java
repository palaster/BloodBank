package palaster.bb.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.BBApi;
import palaster.bb.items.ItemModStaff;
import palaster.bb.network.AbstractMessage.AbstractServerMessage;

public class KeyClickMessage extends AbstractServerMessage<KeyClickMessage> {

	public KeyClickMessage() {}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(!player.worldObj.isRemote) {
			if(BBApi.getMaxBlood(player) > 0 && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemModStaff)
				if(ItemModStaff.getActiveMax(player.getHeldItemMainhand()) != 0)
					if(ItemModStaff.getActivePower(player.getHeldItemMainhand()) == (ItemModStaff.getActiveMax(player.getHeldItemMainhand()) - 1))
						ItemModStaff.setActivePower(player.getHeldItemMainhand(), 0);
					else
						ItemModStaff.setActivePower(player.getHeldItemMainhand(), ItemModStaff.getActivePower(player.getHeldItemMainhand()) + 1);
		}
	}
}
