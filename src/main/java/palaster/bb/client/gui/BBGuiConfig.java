package palaster.bb.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.libs.LibMod;

@SideOnly(Side.CLIENT)
public class BBGuiConfig extends GuiConfig {

    public BBGuiConfig(GuiScreen guiScreen) {
        super(guiScreen, new ConfigElement(BBEventHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), LibMod.modid, false, false, GuiConfig.getAbridgedConfigPath(BBEventHandler.config.toString()));
    }
}
