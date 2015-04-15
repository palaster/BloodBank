package palaster97.ss.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityRuneInscriber;
import palaster97.ss.inventories.ContainerRuneInscriber;
import palaster97.ss.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiRuneInscriber extends GuiContainer {

	public GuiRuneInscriber(InventoryPlayer invPlayer, TileEntityRuneInscriber ri) {
		super(new ContainerRuneInscriber(invPlayer, ri));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.runeInscriber);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
