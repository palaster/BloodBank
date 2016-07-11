package palaster.bb.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
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
			if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemModStaff) 
				if(ItemModStaff.getActiveMax(player.getHeldItemMainhand()) != 0)
					if(ItemModStaff.getActivePower(player.getHeldItemMainhand()) == (ItemModStaff.getActiveMax(player.getHeldItemMainhand()) - 1))
						ItemModStaff.setActivePower(player.getHeldItemMainhand(), 0);
					else
						ItemModStaff.setActivePower(player.getHeldItemMainhand(), ItemModStaff.getActivePower(player.getHeldItemMainhand()) + 1);
			if(player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof ItemModStaff)
				if(ItemModStaff.getActiveMax(player.getHeldItemOffhand()) != 0)
					if(ItemModStaff.getActivePower(player.getHeldItemOffhand()) == (ItemModStaff.getActiveMax(player.getHeldItemOffhand()) - 1))
						ItemModStaff.setActivePower(player.getHeldItemOffhand(), 0);
					else
						ItemModStaff.setActivePower(player.getHeldItemOffhand(), ItemModStaff.getActivePower(player.getHeldItemOffhand()) + 1);
		}
	}
}
