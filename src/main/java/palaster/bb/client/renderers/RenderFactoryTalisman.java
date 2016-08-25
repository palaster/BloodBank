package palaster.bb.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntityTalisman;
import palaster.bb.items.BBItems;

@SideOnly(Side.CLIENT)
public class RenderFactoryTalisman implements IRenderFactory<EntityTalisman> {

	@Override
	public Render<? super EntityTalisman> createRenderFor(RenderManager manager) { return new RenderThrowable<EntityTalisman>(manager, BBItems.talisman, Minecraft.getMinecraft().getRenderItem()); }
}
