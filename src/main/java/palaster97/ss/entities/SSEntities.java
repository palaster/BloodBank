package palaster97.ss.entities;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.client.renderers.RenderSkeletonMinion;
import palaster97.ss.client.renderers.RenderYinYang;
import palaster97.ss.items.SSItems;

public class SSEntities {

	public static void init() {
		EntityRegistry.registerModEntity(EntitySkeletonMinion.class, "skeletonMinion", 0, ScreamingSouls.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityYinYang.class, "yinYang", 1, ScreamingSouls.instance, 64, 10, true);
	}
	
	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonMinion.class, new RenderSkeletonMinion(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityYinYang.class, new RenderYinYang(Minecraft.getMinecraft().getRenderManager(), SSItems.yinYang, Minecraft.getMinecraft().getRenderItem()));
	}
}
