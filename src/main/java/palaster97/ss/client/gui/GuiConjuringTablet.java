package palaster97.ss.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.tile.TileEntityConjuringTablet;
import palaster97.ss.inventories.ContainerConjuringTablet;
import palaster97.ss.libs.LibResource;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.server.GuiButtonMessage;

@SideOnly(Side.CLIENT)
public class GuiConjuringTablet extends GuiContainer {
	
	private TileEntityConjuringTablet ct;

	public GuiConjuringTablet(InventoryPlayer invPlayer, TileEntityConjuringTablet ct) {
		super(new ContainerConjuringTablet(invPlayer, ct));
		this.ct = ct;
		ySize = 193; 
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.conjuringTablet);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiButton(0, guiLeft + 4, guiTop + 82, 32, 20, StatCollector.translateToLocal("ss.misc.start")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		PacketHandler.sendToServer(new GuiButtonMessage(ct.getPos(), button.id));
		button.enabled = false;
	}
}
