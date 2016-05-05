package palaster.bb.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.inventories.ContainerUndeadMonitor;
import palaster.bb.libs.LibResource;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.server.GuiButtonMessage;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiUndeadMonitor extends GuiContainer {

    private InventoryPlayer invPlayer;

    public GuiUndeadMonitor(InventoryPlayer invPlayer) {
        super(new ContainerUndeadMonitor(invPlayer));
        this.invPlayer = invPlayer;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(LibResource.undeadMonitor);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.vigor"), guiLeft, guiTop, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.attunement"), guiLeft, guiTop, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.strength"), guiLeft, guiTop, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.intelligence"), guiLeft, guiTop, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.faith"), guiLeft, guiTop, 4210752);
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        GuiButton vigorIncrease = new GuiButton(0, guiLeft + 10, guiTop + 30, 20, 20, "->");
        GuiButton attunementIncrease = new GuiButton(1, guiLeft + 30, guiTop + 50, 20, 20, "->");
        GuiButton strengthIncrease = new GuiButton(2, guiLeft + 50, guiTop + 70, 20, 20, "->");
        GuiButton intelligenceIncrease = new GuiButton(3, guiLeft + 70, guiTop + 90, 20, 20, "->");
        GuiButton faithIncrease = new GuiButton(4, guiLeft + 90, guiTop + 110, 20, 20, "->");

        buttonList.add(vigorIncrease);
        buttonList.add(attunementIncrease);
        buttonList.add(strengthIncrease);
        buttonList.add(intelligenceIncrease);
        buttonList.add(faithIncrease);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException { PacketHandler.sendToServer(new GuiButtonMessage("", invPlayer.player.getPosition(), button.id)); }
}
