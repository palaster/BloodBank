package palaster97.ss.client.renderers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import palaster97.ss.entities.EntitySkeletonMinion;

public class RenderFactorySkeletonMinion implements IRenderFactory<EntitySkeletonMinion> {

    @Override
    public Render<? super EntitySkeletonMinion> createRenderFor(RenderManager manager) { return new RenderSkeletonMinion(manager); }
}
