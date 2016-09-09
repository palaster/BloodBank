package palaster.bb.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.blocks.tile.TileEntityDesalinator;
import palaster.bb.inventories.ContainerDesalinator;
import palaster.bb.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiDesalinator extends GuiContainer {

	public GuiDesalinator(InventoryPlayer invPlayer, TileEntityDesalinator d) {
		super(new ContainerDesalinator(invPlayer, d));
		ySize = 132;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(LibResource.DESALINATOR);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
