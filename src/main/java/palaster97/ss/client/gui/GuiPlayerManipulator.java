package palaster97.ss.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityPlayerManipulator;
import palaster97.ss.inventories.ContainerPlayerSoulManipulator;
import palaster97.ss.libs.LibResource;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.server.OpenGuiMessage;

@SideOnly(Side.CLIENT)
public class GuiPlayerManipulator extends GuiContainer {
	
	private TileEntityPlayerManipulator psm;

	public GuiPlayerManipulator(InventoryPlayer invPlayer, TileEntityPlayerManipulator psm) {
		super(new ContainerPlayerSoulManipulator(invPlayer, psm));
		this.psm = psm;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, guiLeft - 50, guiTop + 12, 50, 18, StatCollector.translateToLocal("ss.psm.hotswap")));
		buttonList.add(new GuiButton(1, guiLeft - 50, guiTop + 32, 50, 18, StatCollector.translateToLocal("ss.psm.potions")));
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
		mc.renderEngine.bindTexture(LibResource.playerManipulator_0);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
