package palaster.bb.client.gui;

import java.io.IOException;
import java.lang.ref.WeakReference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.BBApi;
import palaster.bb.inventories.ContainerUndeadMonitor;
import palaster.bb.libs.LibResource;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.server.GuiButtonMessage;

@SideOnly(Side.CLIENT)
public class GuiUndeadMonitor extends GuiContainer {

    private WeakReference<EntityPlayer> player;

    public GuiUndeadMonitor(EntityPlayer player) {
        super(new ContainerUndeadMonitor(player));
        this.player = new WeakReference<EntityPlayer>(player);
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
        fontRendererObj.drawString(I18n.format("bb.undead.soul") + ": " + BBApi.getSoul(player.get()), 6, 6, 4210752);
        fontRendererObj.drawString(I18n.format("bb.undead.focus") + ": " + BBApi.getFocus(player.get()) + " / " + BBApi.getFocusMax(player.get()), 6, 16, 4210752);

        fontRendererObj.drawString(I18n.format("bb.undead.vigor") + ": " + BBApi.getVigor(player.get()), 6, 36, 4210752);
        fontRendererObj.drawString(I18n.format("bb.undead.attunement") + ": " + BBApi.getAttunement(player.get()), 6, 46, 4210752);
        fontRendererObj.drawString(I18n.format("bb.undead.strength") + ": " + BBApi.getStrength(player.get()), 6, 56, 4210752);
        fontRendererObj.drawString(I18n.format("bb.undead.intelligence") + ": " + BBApi.getIntelligence(player.get()), 6, 66, 4210752);
        fontRendererObj.drawString(I18n.format("bb.undead.faith") + ": " + BBApi.getFaith(player.get()), 6, 76, 4210752);

        if(BBApi.getSoulCostForNextLevel(player.get()) > BBApi.getSoul(player.get())) {
            fontRendererObj.drawString(I18n.format("bb.undead.soulCost") + ": " + BBApi.getSoulCostForNextLevel(player.get()), 6, 96, 0x8A0707);
        } else if(BBApi.getSoulCostForNextLevel(player.get()) <= 0) {
            fontRendererObj.drawString(I18n.format("bb.undead.soulCost") + ": Free", 6, 96, 0x009900);
        } else {
        	fontRendererObj.drawString(I18n.format("bb.undead.soulCost") + ": " + BBApi.getSoulCostForNextLevel(player.get()), 6, 96, 0x009900);
        }

        /*
        TODO: Fix bonfire not being found on client side.
        if(BBWorldHelper.findBlockVicinityFromPlayer(BBBlocks.bonfire, invPlayer.player.worldObj, invPlayer.player, 10, 4) == null)
            fontRendererObj.drawString(I18n.translateToLocal("bb.undead.bonFireNotFound"), 6, 106, 0x8A0707);
        */
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
    protected void actionPerformed(GuiButton button) throws IOException { PacketHandler.sendToServer(new GuiButtonMessage("", player.get().getPosition(), button.id)); }
}
