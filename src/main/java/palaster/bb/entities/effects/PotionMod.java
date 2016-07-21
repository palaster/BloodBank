package palaster.bb.entities.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.libs.LibMod;
import palaster.bb.libs.LibResource;

public class PotionMod extends Potion {

    private final int iconIndex;

    public PotionMod(String name, boolean isBadEffectIn, int liquidColorIn, int iconIndex) {
        super(isBadEffectIn, liquidColorIn);
        GameRegistry.register(this, new ResourceLocation(LibMod.modid, name));
        setPotionName("effect." + name);
        this.iconIndex = iconIndex;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_) {}

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) { render(x + 6, y + 7, 1); }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) { render(x + 3, y + 3, alpha); }

    @SideOnly(Side.CLIENT)
    private void render(int x, int y, float alpha) {
        Minecraft.getMinecraft().renderEngine.bindTexture(LibResource.bbPotions);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buf = tessellator.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.color(1, 1, 1, alpha);
        int textureX = iconIndex % 8 * 18;
        int textureY = 198 + iconIndex / 8 * 18;
        buf.pos(x, y + 18, 0).tex(textureX * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y, 0).tex((textureX + 18) * 0.00390625, textureY * 0.00390625).endVertex();
        buf.pos(x, y, 0).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex();
        tessellator.draw();
    }
}
