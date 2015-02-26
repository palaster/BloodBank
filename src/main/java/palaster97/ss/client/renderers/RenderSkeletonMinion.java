package palaster97.ss.client.renderers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.client.models.ModelSkeletonMinion;
import palaster97.ss.entities.EntitySkeletonMinion;

@SideOnly(Side.CLIENT)
public class RenderSkeletonMinion extends RenderBiped {
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

	public RenderSkeletonMinion(RenderManager p_i46168_1_) {
		super(p_i46168_1_, new ModelSkeletonMinion(), 0.5f);
		addLayer(new LayerHeldItem(this));
        addLayer(new LayerBipedArmor(this) {
        	@Override
            protected void func_177177_a() {
                field_177189_c = new ModelSkeletonMinion(0.5F, true);
                field_177186_d = new ModelSkeletonMinion(1.0F, true);
            }
        });
	}
	
    protected void preRenderCallback(EntitySkeletonMinion p_77041_1_, float p_77041_2_) {
        if(p_77041_1_.getSkeletonType() == 1)
            GlStateManager.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public void func_82422_c() { GlStateManager.translate(0.09375F, 0.1875F, 0.0F); }

    protected ResourceLocation func_180577_a(EntitySkeletonMinion p_180577_1_) { return p_180577_1_.getSkeletonType() == 1 ? witherSkeletonTextures : skeletonTextures; }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) { return func_180577_a((EntitySkeletonMinion)p_110775_1_); }

    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) { preRenderCallback((EntitySkeletonMinion)p_77041_1_, p_77041_2_); }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) { return func_180577_a((EntitySkeletonMinion)p_110775_1_); }
}
