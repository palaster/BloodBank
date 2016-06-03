package palaster.bb.client.renderers;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.client.models.ModelItztliTablet;
import palaster.bb.entities.EntityItztiliTablet;
import palaster.bb.libs.LibResource;

@SideOnly(Side.CLIENT)
public class RenderItztiliTablet extends RenderLiving<EntityItztiliTablet> {

    public RenderItztiliTablet(RenderManager manager) {
        super(manager, new ModelItztliTablet(), .5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityItztiliTablet entity) { return LibResource.itztiliTablet; }
}
