package palaster.bb.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntityYinYang;
import palaster.bb.items.BBItems;

@SideOnly(Side.CLIENT)
public class RenderFactoryYinYang implements IRenderFactory<EntityYinYang> {

    @Override
    public Render<? super EntityYinYang> createRenderFor(RenderManager manager) { return new RenderThrowable<EntityYinYang>(manager, BBItems.yinYang, Minecraft.getMinecraft().getRenderItem()); }
}
