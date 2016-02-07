package palaster.bb.client.renderers;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.client.models.ModelBeaver;
import palaster.bb.entities.EntityBeaver;
import palaster.bb.libs.LibResource;

@SideOnly(Side.CLIENT)
public class RenderBeaver extends RenderLiving<EntityBeaver> {

    public RenderBeaver(RenderManager renderManager) {
        super(renderManager, new ModelBeaver(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBeaver entity) { return LibResource.beaver; }
}
