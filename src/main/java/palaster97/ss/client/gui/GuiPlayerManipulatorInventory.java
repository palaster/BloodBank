package palaster97.ss.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityPlayerManipulator;
import palaster97.ss.inventories.ContainerPlayerSoulManipulatorInventory;
import palaster97.ss.libs.LibResource;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.server.OpenGuiMessage;

@SideOnly(Side.CLIENT)
public class GuiPlayerManipulatorInventory extends GuiContainer {
	
	private TileEntityPlayerManipulator psm;

	public GuiPlayerManipulatorInventory(InventoryPlayer invPlayer, TileEntityPlayerManipulator psm) {
		super(new ContainerPlayerSoulManipulatorInventory(invPlayer, psm));
		this.psm = psm;
		ySize = 190;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, guiLeft - 24, guiTop + 12, 18, 18, "TEST_0"));
		buttonList.add(new GuiButton(1, guiLeft - 24, guiTop + 32, 18, 18, "TEST_2"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button != null) {
			if(button.id == 0)
				PacketHandler.sendToServer(new OpenGuiMessage(2, psm.getPos()));
			else if(button.id == 1)
				PacketHandler.sendToServer(new OpenGuiMessage(5, psm.getPos()));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.playerManipulator_1);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
