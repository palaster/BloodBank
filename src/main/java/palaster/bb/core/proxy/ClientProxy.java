package palaster.bb.core.proxy;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.entities.BBEntities;
import palaster.bb.items.BBItems;

public class ClientProxy extends CommonProxy {
	
	public static KeyBinding staffChange = new KeyBinding("key.staffChange", Keyboard.KEY_U, "key.categories.bb");
	public static boolean isItemInOffHandRenderingOverlay = false;
	
	@Override
	public void preInit() {
		super.preInit();
		BBBlocks.registerCustomModelResourceLocation();
		BBItems.registerCustomModelResourceLocation();
		BBEntities.registerEntityRenderers();
	}

	@Override
	public void init() {
		super.init();
		ClientRegistry.registerKeyBinding(staffChange);
	}

	@Override
	public void postInit() { super.postInit(); }

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx)); }
	
	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx)); }
}
