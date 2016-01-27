package palaster97.ss.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import palaster97.ss.entities.EntityYinYang;
import palaster97.ss.items.SSItems;

public class RenderFactoryYinYang implements IRenderFactory<EntityYinYang> {

    @Override
    public Render<? super EntityYinYang> createRenderFor(RenderManager manager) { return new RenderYinYang(manager, SSItems.yinYang, Minecraft.getMinecraft().getRenderItem()); }
}
