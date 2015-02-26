package palaster97.ss.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.inventories.ContainerPlayerSoulManipulator;
import palaster97.ss.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiPlayerSoulManipulator extends GuiContainer {

	public GuiPlayerSoulManipulator(InventoryPlayer invPlayer, TileEntityPlayerSoulManipulator psm) { super(new ContainerPlayerSoulManipulator(invPlayer, psm)); }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.playerSoulManipulator);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
