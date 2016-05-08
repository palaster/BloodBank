package palaster.bb.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.BBApi;
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
        ySize = 131;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(LibResource.undeadMonitor);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.soul") + ": " + BBApi.getSoul(invPlayer.player), 6, 6, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.focus") + ": " + BBApi.getFocus(invPlayer.player), 6, 16, 4210752);

        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.vigor") + ": " + BBApi.getVigor(invPlayer.player), 6, 36, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.attunement") + ": " + BBApi.getAttunement(invPlayer.player), 6, 46, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.strength") + ": " + BBApi.getStrength(invPlayer.player), 6, 56, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.intelligence") + ": " + BBApi.getIntelligence(invPlayer.player), 6, 66, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("bb.undead.faith") + ": " + BBApi.getFaith(invPlayer.player), 6, 76, 4210752);
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        GuiButton vigorIncrease = new GuiButton(0, guiLeft + 100, guiTop + 36, 12, 10, "->");
        GuiButton attunementIncrease = new GuiButton(1, guiLeft + 100, guiTop + 46, 12, 10, "->");
        GuiButton strengthIncrease = new GuiButton(2, guiLeft + 100, guiTop + 56, 12, 10, "->");
        GuiButton intelligenceIncrease = new GuiButton(3, guiLeft + 100, guiTop + 66, 12, 10, "->");
        GuiButton faithIncrease = new GuiButton(4, guiLeft + 100, guiTop + 76, 12, 10, "->");

        buttonList.add(vigorIncrease);
        buttonList.add(attunementIncrease);
        buttonList.add(strengthIncrease);
        buttonList.add(intelligenceIncrease);
        buttonList.add(faithIncrease);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException { PacketHandler.sendToServer(new GuiButtonMessage("", invPlayer.player.getPosition(), button.id)); }
}
