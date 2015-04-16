package palaster97.ss.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.inventories.ContainerInscriptionKit;
import palaster97.ss.inventories.InventoryInscriptionKit;
import palaster97.ss.libs.LibResource;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.server.GuiButtonMessage;

@SideOnly(Side.CLIENT)
public class GuiInscriptionKit extends GuiContainer {
	
	private InventoryPlayer invPlayer;

	public GuiInscriptionKit(InventoryPlayer invPlayer, InventoryInscriptionKit ik) {
		super(new ContainerInscriptionKit(invPlayer, ik));
		this.invPlayer = invPlayer;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.inscriptionKit);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiButton(0, guiLeft + 7, guiTop + 56, 32, 20, StatCollector.translateToLocal("ss.misc.start")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		PacketHandler.sendToServer(new GuiButtonMessage(invPlayer.player.getPosition(), button.id));
		button.enabled = false;
	}
}
