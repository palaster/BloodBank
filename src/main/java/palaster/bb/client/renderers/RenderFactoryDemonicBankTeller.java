package palaster.bb.client.renderers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntityDemonicBankTeller;

@SideOnly(Side.CLIENT)
public class RenderFactoryDemonicBankTeller implements IRenderFactory<EntityDemonicBankTeller> {

    @Override
    public Render<? super EntityDemonicBankTeller> createRenderFor(RenderManager manager) {
        return new RenderDemonicBankTeller(manager);
    }
}
