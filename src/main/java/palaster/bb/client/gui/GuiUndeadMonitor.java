package palaster.bb.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import palaster.bb.inventories.ContainerUndeadMonitor;
import palaster.bb.libs.LibResource;

public class GuiUndeadMonitor extends GuiContainer {

    public GuiUndeadMonitor(InventoryPlayer invPlayer) { super(new ContainerUndeadMonitor(invPlayer)); }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(LibResource.undeadMonitor);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
