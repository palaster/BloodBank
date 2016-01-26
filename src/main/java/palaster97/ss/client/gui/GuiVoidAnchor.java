package palaster97.ss.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityModInventory;
import palaster97.ss.inventories.ContainerVoidAnchor;
import palaster97.ss.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiVoidAnchor extends GuiContainer {

	public GuiVoidAnchor(InventoryPlayer invPlayer, TileEntityModInventory va) {
		super(new ContainerVoidAnchor(invPlayer, va));
		ySize = 150;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(LibResource.voidAnchor);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
