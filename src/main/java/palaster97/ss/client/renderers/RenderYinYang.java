package palaster97.ss.client.renderers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderYinYang extends Render {
	
	protected final Item field_177084_a;
    private final RenderItem field_177083_e;

	public RenderYinYang(RenderManager renderManager, Item p_i46137_2_, RenderItem p_i46137_3_) {
		super(renderManager);
		field_177084_a = p_i46137_2_;
        field_177083_e = p_i46137_3_;
	}
	
	@Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        bindTexture(TextureMap.locationBlocksTexture);
        field_177083_e.renderItem(func_177082_d(entity), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }

    public ItemStack func_177082_d(Entity p_177082_1_) { return new ItemStack(field_177084_a, 1, 0); }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) { return TextureMap.locationBlocksTexture; }
}
