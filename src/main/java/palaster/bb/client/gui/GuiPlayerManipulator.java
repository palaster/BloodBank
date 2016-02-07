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
import palaster.bb.inventories.ContainerPlayerSoulManipulator;
import palaster.bb.libs.LibResource;
import palaster.bb.network.server.OpenGuiMessage;
import palaster.bb.network.PacketHandler;

@SideOnly(Side.CLIENT)
public class GuiPlayerManipulator extends GuiContainer {
	
	private TileEntityPlayerManipulator psm;

	public GuiPlayerManipulator(InventoryPlayer invPlayer, TileEntityPlayerManipulator psm) {
		super(new ContainerPlayerSoulManipulator(invPlayer, psm));
		this.psm = psm;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, guiLeft - 50, guiTop + 12, 50, 18, StatCollector.translateToLocal("bb.psm.hotswap")));
		buttonList.add(new GuiButton(1, guiLeft - 50, guiTop + 32, 50, 18, StatCollector.translateToLocal("bb.psm.potions")));
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
