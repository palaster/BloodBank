package palaster.bb.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.capabilities.entities.BloodBankCapability.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.network.AbstractMessage;

public class UpdateBloodMessage extends AbstractMessage.AbstractClientMessage<UpdateBloodMessage> {
	
	private NBTTagCompound tag;
	
	public UpdateBloodMessage() {}

    public UpdateBloodMessage(NBTTagCompound tag) { this.tag = tag; }

	@Override
	protected void read(PacketBuffer buffer) throws IOException { tag = ByteBufUtils.readTag(buffer); }

	@Override
	protected void write(PacketBuffer buffer) throws IOException { ByteBufUtils.writeTag(buffer, tag); }

	@Override
	protected void process(EntityPlayer player, Side side) {
		final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
		if(bloodBank != null)
			bloodBank.loadNBT(tag);
	}
}
