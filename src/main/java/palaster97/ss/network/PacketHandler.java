package palaster97.ss.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.libs.LibMod;
import palaster97.ss.network.client.SyncPlayerPropsMessage;
import palaster97.ss.network.server.ChangeBlockMessage;
import palaster97.ss.network.server.GuiButtonMessage;
import palaster97.ss.network.server.MiddleClickMessage;
import palaster97.ss.network.server.OpenGuiMessage;

public class PacketHandler {
	
	private static byte packetId = 0;
	private static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(LibMod.modid);

	public static final void registerPackets() {
		registerMessage(OpenGuiMessage.class);
		registerMessage(SyncPlayerPropsMessage.class);
		registerMessage(ChangeBlockMessage.class);
		registerMessage(GuiButtonMessage.class);
		registerMessage(MiddleClickMessage.class);
	}
	
	private static final <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(Class<T> clazz) {
		if (AbstractMessage.AbstractClientMessage.class.isAssignableFrom(clazz))
			PacketHandler.instance.registerMessage(clazz, clazz, packetId++, Side.CLIENT);
		else if (AbstractMessage.AbstractServerMessage.class.isAssignableFrom(clazz))
			PacketHandler.instance.registerMessage(clazz, clazz, packetId++, Side.SERVER);
		else {
			PacketHandler.instance.registerMessage(clazz, clazz, packetId, Side.CLIENT);
			PacketHandler.instance.registerMessage(clazz, clazz, packetId++, Side.SERVER);
		}
	}

	public static final void sendTo(IMessage message, EntityPlayerMP player) { PacketHandler.instance.sendTo(message, player); }

	public static void sendToAll(IMessage message) { PacketHandler.instance.sendToAll(message); }

	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) { PacketHandler.instance.sendToAllAround(message, point); }

	public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) { PacketHandler.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range)); }

	public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) { PacketHandler.sendToAllAround(message, player.worldObj.provider.getDimensionId(), player.posX, player.posY, player.posZ, range); }

	public static final void sendToDimension(IMessage message, int dimensionId) { PacketHandler.instance.sendToDimension(message, dimensionId); }

	public static final void sendToServer(IMessage message) { PacketHandler.instance.sendToServer(message); }
}
