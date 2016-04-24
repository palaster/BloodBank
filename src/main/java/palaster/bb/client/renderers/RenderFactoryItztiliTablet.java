package palaster.bb.client.renderers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntityItztiliTablet;

@SideOnly(Side.CLIENT)
public class RenderFactoryItztiliTablet implements IRenderFactory<EntityItztiliTablet> {

    @Override
    public Render<? super EntityItztiliTablet> createRenderFor(RenderManager manager) { return new RenderItztiliTablet(manager); }
}
