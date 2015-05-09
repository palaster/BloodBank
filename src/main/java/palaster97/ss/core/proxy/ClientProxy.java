package palaster97.ss.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster97.ss.core.handlers.SSHUDHandler;
import palaster97.ss.entities.SSEntities;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {super.preInit(); }
	
	// Register Renderers Here
	@Override
	public void init() {
		super.init();
		SSEntities.registerEntityRenderers();
		MinecraftForge.EVENT_BUS.register(new SSHUDHandler());
	}
	
	@Override
	public void postInit() { super.postInit(); }
	
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx)); }
	
	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx)); }
}
