package palaster.bb.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.lwjgl.input.Keyboard;
import palaster.bb.entities.BBEntities;

public class ClientProxy extends CommonProxy {

	public static KeyBinding staffChange;
	
	@Override
	public void preInit() {
		super.preInit();
		BBEntities.registerEntityRenderers();
	}

	@Override
	public void init() {
		super.init();

		staffChange = new KeyBinding("key.staffChange", Keyboard.KEY_U, "key.categories.bb");
		ClientRegistry.registerKeyBinding(staffChange);
	}

	@Override
	public void postInit() { super.postInit(); }

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx)); }
	
	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx)); }
}
