package palaster.bb.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.libpal.network.AbstractMessage;

public class UpdateRPGMessage extends AbstractMessage.AbstractClientMessage<UpdateRPGMessage> {

	private NBTTagCompound tag;
	
	public UpdateRPGMessage() {}

    public UpdateRPGMessage(NBTTagCompound tag) { this.tag = tag; }

	@Override
	protected void read(PacketBuffer buffer) throws IOException { tag = ByteBufUtils.readTag(buffer); }

	@Override
	protected void write(PacketBuffer buffer) throws IOException { ByteBufUtils.writeTag(buffer, tag); }

	@Override
	protected void process(EntityPlayer player, Side side) {
		final IRPG rpg = RPGCapabilityProvider.get(player);
		if(rpg != null)
			rpg.loadNBT(player, tag);
	}
}
