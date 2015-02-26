package palaster97.ss.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.inventories.ContainerSpace;
import palaster97.ss.inventories.InventorySpace;
import palaster97.ss.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiSpace extends GuiContainer {

	private InventorySpace invSpace;
	
	public GuiSpace(InventoryPlayer invPlayer, InventorySpace invSpace) {
		super(new ContainerSpace(invPlayer, invSpace));
		this.invSpace = invSpace;
		ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        fontRendererObj.drawString(invSpace.hasCustomName() ? invSpace.getName() : I18n.format(invSpace.getName(), new Object[0]), 8, 6, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(LibResource.space);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
