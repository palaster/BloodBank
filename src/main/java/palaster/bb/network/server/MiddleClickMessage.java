package palaster.bb.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.capabilities.entities.IBloodBank;
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
		final IBloodBank bloodBank = player.getCapability(BloodBankCapabilityProvider.bloodBankCap, null);
		if(bloodBank != null)
			if(player.getHeldItemMainhand() != null)
				if(player.getHeldItemMainhand().getItem() instanceof ItemModStaff)
					if(ItemModStaff.getActiveMax(player.getHeldItemMainhand()) != 0)
						if(ItemModStaff.getActivePower(player.getHeldItemMainhand()) == (ItemModStaff.getActiveMax(player.getHeldItemMainhand()) - 1))
							ItemModStaff.setActivePower(player.getHeldItemMainhand(), 0);
						else
							ItemModStaff.setActivePower(player.getHeldItemMainhand(), ItemModStaff.getActivePower(player.getHeldItemMainhand()) + 1);
	}
}
