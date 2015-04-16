package palaster97.ss.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.blocks.tile.TileEntityConjuringTablet;
import palaster97.ss.blocks.tile.TileEntityModInventory;
import palaster97.ss.inventories.InventoryInscriptionKit;
import palaster97.ss.network.AbstractMessage.AbstractServerMessage;

public class GuiButtonMessage extends AbstractServerMessage<GuiButtonMessage> {
	
	private BlockPos pos;
	private int id;

	public GuiButtonMessage() {}
	
	public GuiButtonMessage(BlockPos pos, int id) {
		this.pos = pos;
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		pos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
		id = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		if(pos != null) {
			buffer.writeInt(pos.getX());
			buffer.writeInt(pos.getY());
			buffer.writeInt(pos.getZ());
		}
		buffer.writeInt(id);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.worldObj.getTileEntity(pos);
		if(te != null && te instanceof TileEntityConjuringTablet)
			((TileEntityConjuringTablet) te).trySummon(player);
		if(te != null && te instanceof TileEntityModInventory)
			((TileEntityModInventory) te).receiveButtonEvent(id, player);
		if(te == null)
			new InventoryInscriptionKit(player.getHeldItem()).receiveButtonEvent(id, player);
	}
}
