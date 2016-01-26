package palaster97.ss.network;

import com.google.common.base.Throwables;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import palaster97.ss.ScreamingSouls;

import java.io.IOException;

public abstract class AbstractMessage<T extends AbstractMessage<T>> implements IMessage, IMessageHandler <T, IMessage> {

	protected abstract void read(PacketBuffer buffer) throws IOException;
	
	protected abstract void write(PacketBuffer buffer) throws IOException;

	public abstract void process(EntityPlayer player, Side side);

	protected boolean isValidOnSide(Side side) { return true; }

	protected boolean requiresMainThread() { return true; }

	@Override
	public void fromBytes(ByteBuf buffer) {
		try {
			read(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		try { 
			write(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
	public final IMessage onMessage(T msg, MessageContext ctx) {
		if(!msg.isValidOnSide(ctx.side))
			throw new RuntimeException("Invalid side " + ctx.side.name() + " for " + msg.getClass().getSimpleName());
		else if(msg.requiresMainThread())
			checkThreadAndEnqueue(msg, ctx);
		else
			msg.process(ScreamingSouls.proxy.getPlayerEntity(ctx), ctx.side);
		return null;
	}

	private static final <T extends AbstractMessage<T>> void checkThreadAndEnqueue(final AbstractMessage<T> msg, final MessageContext ctx) {
		IThreadListener thread = ScreamingSouls.proxy.getThreadFromContext(ctx);
		thread.addScheduledTask(new Runnable() {
			@Override
			public void run() { msg.process(ScreamingSouls.proxy.getPlayerEntity(ctx), ctx.side); }
		});
	}

	public static abstract class AbstractClientMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) { return side.isClient(); }
	}

	public static abstract class AbstractServerMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) { return side.isServer(); }
	}
}