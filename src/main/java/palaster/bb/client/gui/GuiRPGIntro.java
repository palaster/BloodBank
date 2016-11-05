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
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityDefault;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.inventories.ContainerRPGIntro;
import palaster.bb.libs.LibResource;
import palaster.bb.network.server.GuiButtonMessage;
import palaster.libpal.network.PacketHandler;

@SideOnly(Side.CLIENT)
public class GuiRPGIntro extends GuiContainer {
	
	private WeakReference<EntityPlayer> player;

	public GuiRPGIntro(EntityPlayer player) {
		super(new ContainerRPGIntro());
		this.player = new WeakReference<EntityPlayer>(player);
		ySize = 160;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(LibResource.BLANK);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    	if(player != null && player.get() != null) {
    		final IRPG rpg = RPGCapabilityProvider.get(player.get());
    		if(rpg != null) {
    			if(rpg.getCareer() != null)
    				fontRendererObj.drawString(I18n.format("bb.career.base") + ": " + I18n.format(rpg.getCareer().toString()), 6, 6, 4210752);
    			else
    				fontRendererObj.drawString(I18n.format("bb.career.base") + ": " + I18n.format("bb.career.noCareer"), 6, 6, 4210752);
                fontRendererObj.drawString(I18n.format("bb.rpg.constitution") + ": " + rpg.getConstitution(), 6, 26, 4210752);
                fontRendererObj.drawString(I18n.format("bb.rpg.strength") + ": " + rpg.getStrength(), 6, 36, 4210752);
                fontRendererObj.drawString(I18n.format("bb.rpg.defense") + ": " + rpg.getDefense(), 6, 46, 4210752);
                fontRendererObj.drawString(I18n.format("bb.rpg.dexterity") + ": " + rpg.getDexterity(), 6, 56, 4210752);
                fontRendererObj.drawString(I18n.format("bb.rpg.intelligence") + ": " + rpg.getIntelligence(), 6, 66, 4210752);
                fontRendererObj.drawString(I18n.format("bb.rpg.magick") + ": " + rpg.getMagick() + "/" + rpg.getMaxMagick(), 96, 66, 4210752);
                if(RPGCapabilityDefault.getExperienceCostForNextLevel(player.get()) > player.get().experienceLevel)
                	fontRendererObj.drawString(I18n.format("bb.expCost") + ": " + RPGCapabilityDefault.getExperienceCostForNextLevel(player.get()) + "", 6, 78, 0x8A0707);
                else
                	fontRendererObj.drawString(I18n.format("bb.expCost") + ": " + RPGCapabilityDefault.getExperienceCostForNextLevel(player.get()) + "", 6, 78, 0x009900);
                if(rpg.getCareer() != null)
                	rpg.getCareer().drawExtraInformation(this, player.get(), fontRendererObj, mouseX, mouseY);
    		}
    	}
    }
	
	@Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        GuiButton constitutionIncrease = new GuiButton(0, guiLeft + 82, guiTop + 26, 12, 10, "->");
        GuiButton strengthIncrease = new GuiButton(1, guiLeft + 82, guiTop + 36, 12, 10, "->");
        GuiButton defenseIncrease = new GuiButton(2, guiLeft + 82, guiTop + 46, 12, 10, "->");
        GuiButton dexterityIncrease = new GuiButton(3, guiLeft + 82, guiTop + 56, 12, 10, "->");
        GuiButton intelligenceIncrease = new GuiButton(4, guiLeft + 82, guiTop + 66, 12, 10, "->");

        buttonList.add(constitutionIncrease);
        buttonList.add(strengthIncrease);
        buttonList.add(defenseIncrease);
        buttonList.add(dexterityIncrease);
        buttonList.add(intelligenceIncrease);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException { PacketHandler.sendToServer(new GuiButtonMessage("", player.get().getPosition(), button.id)); }
}