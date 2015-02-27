package palaster97.ss.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import palaster97.ss.blocks.tile.TileEntityPlayerStatue;
import palaster97.ss.inventories.ContainerPlayerStatue;
import palaster97.ss.libs.LibResource;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.server.ChangeStringMessage;

@SideOnly(Side.CLIENT)
public class GuiPlayerStatue extends GuiContainer {

	private TileEntityPlayerStatue ps;
	private int value;
	private GuiTextField name;
	
	public GuiPlayerStatue(InventoryPlayer invPlayer, TileEntityPlayerStatue ps, int value) {
		super(new ContainerPlayerStatue(invPlayer, ps, value));
		this.ps = ps;
		this.value = value;
		if(value == 0)
			ySize = 133;
		else
			ySize = 184; 
	}
	
	@Override
	public void initGui() {
		super.initGui();
		if(value == 0) {
			Keyboard.enableRepeatEvents(true);
			name = new GuiTextField(0, fontRendererObj, guiLeft + 28, guiTop + 18, 122, 26);
	        name.setTextColor(-1);
	        name.setDisabledTextColour(-1);
	        name.setEnableBackgroundDrawing(false);
	        name.setMaxStringLength(16);
	        
	        buttonList.add(new GuiButtonExt(0, guiLeft + 7, guiTop + 32, 38, 16, "Enter"));
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(value == 0)
			if(button.id == 0) {
				PacketHandler.sendToServer(new ChangeStringMessage(ps.getPos(), name.getText()));
				button.enabled = false;
			}
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if(value == 0)
			Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(value == 0 && name.textboxKeyTyped(typedChar, keyCode)) {} 
		else
			super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(value == 0)
			name.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		if(value == 0) {
			GlStateManager.disableLighting();
			GlStateManager.disableBlend();
			name.drawTextBox();
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if(value == 0)
			fontRendererObj.drawString(I18n.format(StatCollector.translateToLocal("ss.misc.uuid") + ": " + ps.getUUIDName(), new Object[0]), 26, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		if(value == 0)
			mc.renderEngine.bindTexture(LibResource.playerStatue_0);
		else
			mc.renderEngine.bindTexture(LibResource.playerStatue_1);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
