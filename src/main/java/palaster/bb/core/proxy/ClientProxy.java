package palaster.bb.core.proxy;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.entities.BBEntities;
import palaster.bb.items.BBItems;

public class ClientProxy extends CommonProxy {
	
	public static KeyBinding staffChange = new KeyBinding("key.staffChange", Keyboard.KEY_U, "key.categories.bb");
	
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
}
