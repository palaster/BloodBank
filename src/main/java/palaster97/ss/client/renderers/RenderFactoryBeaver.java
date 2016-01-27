package palaster97.ss.client.renderers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import palaster97.ss.entities.EntityBeaver;

public class RenderFactoryBeaver implements IRenderFactory<EntityBeaver> {

    @Override
    public Render<? super EntityBeaver> createRenderFor(RenderManager manager) { return new RenderBeaver(manager); }
}
