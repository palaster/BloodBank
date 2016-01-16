package palaster97.ss.core.handlers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster97.ss.libs.LibMod;

import java.io.File;

public class ConfigurationHandler {

    public static Configuration config;

    public static boolean compatBloodMagic = true;

    public static void init(File configFile) {
        if(config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent e) {
        if(e.modID.equalsIgnoreCase(LibMod.modid))
            loadConfiguration();
    }

    private static void loadConfiguration() {
        compatBloodMagic = config.getBoolean("Compatibility Blood Magic", Configuration.CATEGORY_GENERAL, true, "Turns off own ways of getting blood");
        if(config.hasChanged())
            config.save();
    }
}
