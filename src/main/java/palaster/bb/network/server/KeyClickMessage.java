package palaster.bb.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.items.ItemModStaff;
import palaster.bb.network.AbstractMessage.AbstractServerMessage;

import java.io.IOException;

public class KeyClickMessage extends AbstractServerMessage<KeyClickMessage> {

	public KeyClickMessage() {}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(!player.worldObj.isRemote) {
			final IBloodBank bloodBank = player.getCapability(BloodBankCapabilityProvider.bloodBankCap, null);
			if(bloodBank != null && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemModStaff)
				if(ItemModStaff.getActiveMax(player.getHeldItemMainhand()) != 0)
					if(ItemModStaff.getActivePower(player.getHeldItemMainhand()) == (ItemModStaff.getActiveMax(player.getHeldItemMainhand()) - 1))
						ItemModStaff.setActivePower(player.getHeldItemMainhand(), 0);
					else
						ItemModStaff.setActivePower(player.getHeldItemMainhand(), ItemModStaff.getActivePower(player.getHeldItemMainhand()) + 1);
		}
	}
}
