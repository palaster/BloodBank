package palaster.bb.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.blocks.tile.TileEntityPlayerManipulator;
import palaster.bb.libs.LibResource;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.server.OpenGuiMessage;
import palaster.bb.inventories.ContainerPlayerSoulManipulatorPotion;

@SideOnly(Side.CLIENT)
public class GuiPlayerManipulatorPotion extends GuiContainer {
	
	private TileEntityPlayerManipulator psm;

	public GuiPlayerManipulatorPotion(InventoryPlayer invPlayer, TileEntityPlayerManipulator psm) {
		super(new ContainerPlayerSoulManipulatorPotion(invPlayer, psm));
		this.psm = psm;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, guiLeft - 50, guiTop + 12, 50, 18, StatCollector.translateToLocal("bb.psm.home")));
		buttonList.add(new GuiButton(1, guiLeft - 50, guiTop + 32, 50, 18, StatCollector.translateToLocal("bb.psm.hotswap")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button != null) {
			if(button.id == 0)
				PacketHandler.sendToServer(new OpenGuiMessage(2, psm.getPos()));
			else if(button.id == 1)
				PacketHandler.sendToServer(new OpenGuiMessage(4, psm.getPos()));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.playerManipulator_2);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
