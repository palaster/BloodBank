package palaster97.ss.client.renderers;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import palaster97.ss.entities.EntityDemonicBankTeller;

public class RenderDemonicBankTeller extends RenderLiving<EntityDemonicBankTeller> {

    public RenderDemonicBankTeller(RenderManager manager) {
        super(manager, null, .5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDemonicBankTeller entity) {
        return null;
    }
}
