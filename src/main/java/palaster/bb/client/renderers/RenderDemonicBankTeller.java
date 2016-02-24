package palaster.bb.client.renderers;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntityDemonicBankTeller;
import palaster.bb.libs.LibResource;

@SideOnly(Side.CLIENT)
public class RenderDemonicBankTeller extends RenderLiving<EntityDemonicBankTeller> {

    public RenderDemonicBankTeller(RenderManager manager) {
        super(manager, new ModelZombie(0.0F, true), .5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDemonicBankTeller entity) { return LibResource.bankTeller; }
}
