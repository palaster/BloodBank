package palaster.bb.client.renderers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.client.models.ModelSkeletonMinion;
import palaster.bb.entities.EntitySkeletonMinion;

@SideOnly(Side.CLIENT)
public class RenderSkeletonMinion extends RenderBiped<EntitySkeletonMinion> {

	public RenderSkeletonMinion(RenderManager p_i46168_1_) {
		super(p_i46168_1_, new ModelSkeletonMinion(), 0.5f);
		addLayer(new LayerHeldItem(this));
        addLayer(new LayerBipedArmor(this) {
        	@Override
            protected void initArmor() {
                modelLeggings = new ModelSkeletonMinion(0.5F, true);
                modelArmor = new ModelSkeletonMinion(1.0F, true);
            }
        });
	}

    @Override
    protected void preRenderCallback(EntitySkeletonMinion p_77041_1_, float p_77041_2_) { GlStateManager.translate(0.09375F, 0.1875F, 0.0F); }

    @Override
    protected ResourceLocation getEntityTexture(EntitySkeletonMinion p_180577_1_) { return p_180577_1_.getSkeletonType() == 1 ? new ResourceLocation("textures/entity/skeleton/wither_skeleton.png") : new ResourceLocation("textures/entity/skeleton/skeleton.png"); }
}
