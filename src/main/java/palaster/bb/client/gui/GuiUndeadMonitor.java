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
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.core.helpers.BBWorldHelper;
import palaster.bb.entities.careers.CareerUndead;
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
    	if(player != null && player.get() != null) {
    		final IRPG rpg = RPGCapabilityProvider.get(player.get());
    		if(rpg != null)
    			if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead) {
    				fontRendererObj.drawString(I18n.format("bb.undead.soul") + ": " + ((CareerUndead) rpg.getCareer()).getSoul(), 6, 6, 4210752);
                    fontRendererObj.drawString(I18n.format("bb.undead.focus") + ": " + ((CareerUndead) rpg.getCareer()).getFocus() + " / " + ((CareerUndead) rpg.getCareer()).getFocusMax(), 6, 16, 4210752);
                    fontRendererObj.drawString(I18n.format("bb.undead.vigor") + ": " + ((CareerUndead) rpg.getCareer()).getVigor(), 6, 36, 4210752);
                    fontRendererObj.drawString(I18n.format("bb.undead.attunement") + ": " + ((CareerUndead) rpg.getCareer()).getAttunement(), 6, 46, 4210752);
                    fontRendererObj.drawString(I18n.format("bb.undead.strength") + ": " + ((CareerUndead) rpg.getCareer()).getStrength(), 6, 56, 4210752);
                    fontRendererObj.drawString(I18n.format("bb.undead.intelligence") + ": " + ((CareerUndead) rpg.getCareer()).getIntelligence(), 6, 66, 4210752);
                    fontRendererObj.drawString(I18n.format("bb.undead.faith") + ": " + ((CareerUndead) rpg.getCareer()).getFaith(), 6, 76, 4210752);
                    if(BBApi.getSoulCostForNextLevel(player.get()) > ((CareerUndead) rpg.getCareer()).getSoul()) {
                        fontRendererObj.drawString(I18n.format("bb.undead.soulCost") + ": " + BBApi.getSoulCostForNextLevel(player.get()), 6, 96, 0x8A0707);
                    } else if(BBApi.getSoulCostForNextLevel(player.get()) <= 0) {
                        fontRendererObj.drawString(I18n.format("bb.undead.soulCost") + ": Free", 6, 96, 0x009900);
                    } else {
                    	fontRendererObj.drawString(I18n.format("bb.undead.soulCost") + ": " + BBApi.getSoulCostForNextLevel(player.get()), 6, 96, 0x009900);
                    }
                    if(BBWorldHelper.findBlockVicinityFromPlayer(BBBlocks.bonfire, player.get().worldObj, player.get(), 10, 4) == null)
                        fontRendererObj.drawString(I18n.format("bb.undead.bonFireNotFound"), 6, 106, 0x8A0707);
    			}
    	}
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
