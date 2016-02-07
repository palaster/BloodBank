package palaster.bb.client.renderers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntitySkeletonMinion;

@SideOnly(Side.CLIENT)
public class RenderFactorySkeletonMinion implements IRenderFactory<EntitySkeletonMinion> {

    @Override
    public Render<? super EntitySkeletonMinion> createRenderFor(RenderManager manager) { return new RenderSkeletonMinion(manager); }
}
