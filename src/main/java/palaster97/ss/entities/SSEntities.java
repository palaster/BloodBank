package palaster97.ss.entities;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import palaster97.ss.client.renderers.RenderSkeletonMinion;

public class SSEntities {

	public static void init() {
		EntityRegistry.registerGlobalEntityID(EntitySkeletonMinion.class, "skeletonMinion", EntityRegistry.findGlobalUniqueEntityId());
	}
	
	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonMinion.class, new RenderSkeletonMinion(Minecraft.getMinecraft().getRenderManager()));
	}
}
