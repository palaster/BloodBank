package palaster.bb.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.inventories.ContainerLetter;
import palaster.bb.inventories.InventoryMod;
import palaster.bb.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiLetter extends GuiContainer {

    public GuiLetter(InventoryPlayer invPlayer, InventoryMod invMod) { super(new ContainerLetter(invPlayer, invMod)); }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(LibResource.letter);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
