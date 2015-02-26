package palaster97.ss.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.inventories.ContainerShoeElf;
import palaster97.ss.inventories.InventoryShoeElf;
import palaster97.ss.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiShoeElf extends GuiContainer {

	public GuiShoeElf(InventoryPlayer invPlayer, InventoryShoeElf invShoeElf) {
		super(new ContainerShoeElf(invPlayer, invShoeElf));
		ySize = 133;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.guiShoeElf);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
