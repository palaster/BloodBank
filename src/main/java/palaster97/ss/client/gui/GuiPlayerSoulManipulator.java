package palaster97.ss.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.inventories.ContainerPlayerSoulManipulator;
import palaster97.ss.libs.LibResource;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.server.OpenGuiMessage;

@SideOnly(Side.CLIENT)
public class GuiPlayerSoulManipulator extends GuiContainer {
	
	private TileEntityPlayerSoulManipulator psm;

	public GuiPlayerSoulManipulator(InventoryPlayer invPlayer, TileEntityPlayerSoulManipulator psm) {
		super(new ContainerPlayerSoulManipulator(invPlayer, psm));
		this.psm = psm;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, guiLeft - 24, guiTop + 12, 18, 18, "TEST_1"));
		buttonList.add(new GuiButton(1, guiLeft - 24, guiTop + 32, 18, 18, "TEST_2"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button != null) {
			if(button.id == 0)
				PacketHandler.sendToServer(new OpenGuiMessage(4, psm.getPos()));
			else if(button.id == 1)
				PacketHandler.sendToServer(new OpenGuiMessage(5, psm.getPos()));
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.playerSoulManipulator_0);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
