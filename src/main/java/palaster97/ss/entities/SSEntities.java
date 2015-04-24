package palaster97.ss.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import palaster97.ss.client.renderers.RenderBurningChild;
import palaster97.ss.client.renderers.RenderSkeletonMinion;

public class SSEntities {

	public static void init() {
		EntityRegistry.registerGlobalEntityID(EntitySkeletonMinion.class, "skeletonMinion", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityBurningChild.class, "burningChild", EntityRegistry.findGlobalUniqueEntityId(), 0xff5f44, 0x666666);
		
		EntityRegistry.addSpawn(EntityBurningChild.class, 1, 0, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.MAGICAL));
	}
	
	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonMinion.class, new RenderSkeletonMinion(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityBurningChild.class, new RenderBurningChild(Minecraft.getMinecraft().getRenderManager(), new ModelZombie(0.0f, true), 0.5f));
	}
}
