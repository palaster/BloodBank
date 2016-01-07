package palaster97.ss.client.renderers;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.client.models.ModelBeaver;
import palaster97.ss.entities.EntityBeaver;
import palaster97.ss.libs.LibResource;

@SideOnly(Side.CLIENT)
public class RenderBeaver extends RenderLiving<EntityBeaver> {

    public RenderBeaver(RenderManager renderManager) {
        super(renderManager, new ModelBeaver(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBeaver entity) { return LibResource.beaver; }
}
