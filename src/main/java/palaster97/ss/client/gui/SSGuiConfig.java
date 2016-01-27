package palaster97.ss.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.handlers.SSEventHandler;
import palaster97.ss.libs.LibMod;

@SideOnly(Side.CLIENT)
public class SSGuiConfig extends GuiConfig {

    public SSGuiConfig(GuiScreen guiScreen) {
        super(guiScreen, new ConfigElement(SSEventHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), LibMod.modid, true, true, GuiConfig.getAbridgedConfigPath(SSEventHandler.config.toString()));
    }
}
