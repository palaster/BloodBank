package palaster97.ss.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import palaster97.ss.client.models.ModelLittlePeople;
import palaster97.ss.client.renderers.RenderShoeElf;
import palaster97.ss.client.renderers.RenderSkeletonMinion;

public class SSEntities {

	public static void init() {
		EntityRegistry.registerGlobalEntityID(EntitySkeletonMinion.class, "skeletonMinion", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityShoeElf.class, "shoeElf", EntityRegistry.findGlobalUniqueEntityId());
	}
	
	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonMinion.class, new RenderSkeletonMinion(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityShoeElf.class, new RenderShoeElf(Minecraft.getMinecraft().getRenderManager(), new ModelLittlePeople(), 0.3f));
	}
}
