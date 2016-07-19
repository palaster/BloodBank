package palaster.bb.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapability.UndeadCapabilityProvider;
import palaster.bb.network.AbstractMessage;

public class UpdateUndeadMessage extends AbstractMessage.AbstractClientMessage<UpdateUndeadMessage> {

	private NBTTagCompound tag;
	
	public UpdateUndeadMessage() {}

    public UpdateUndeadMessage(NBTTagCompound tag) { this.tag = tag; }

	@Override
	protected void read(PacketBuffer buffer) throws IOException { tag = ByteBufUtils.readTag(buffer); }

	@Override
	protected void write(PacketBuffer buffer) throws IOException { ByteBufUtils.writeTag(buffer, tag); }

	@Override
	protected void process(EntityPlayer player, Side side) {
		final IUndead undead = UndeadCapabilityProvider.get(player);
		if(undead != null)
			undead.loadNBT(tag);
	}
}
