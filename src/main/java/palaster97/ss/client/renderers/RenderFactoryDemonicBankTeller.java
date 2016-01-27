package palaster97.ss.client.renderers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import palaster97.ss.entities.EntityDemonicBankTeller;

public class RenderFactoryDemonicBankTeller implements IRenderFactory<EntityDemonicBankTeller> {

    @Override
    public Render<? super EntityDemonicBankTeller> createRenderFor(RenderManager manager) {
        return new RenderDemonicBankTeller(manager);
    }
}
