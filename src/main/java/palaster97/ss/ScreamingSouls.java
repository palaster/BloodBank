package palaster97.ss;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import palaster97.ss.core.handlers.ConfigurationHandler;
import palaster97.ss.core.proxy.CommonProxy;
import palaster97.ss.libs.LibMod;

@Mod(modid = LibMod.modid, name = LibMod.name, version = LibMod.version, dependencies = LibMod.dependencies, guiFactory = LibMod.guiFactory)
public class ScreamingSouls {
	
	@Instance(LibMod.modid)
	public static ScreamingSouls instance;
	
	@SidedProxy(clientSide = LibMod.client, serverSide = LibMod.server)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		ConfigurationHandler.init(e.getSuggestedConfigurationFile());
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) { proxy.init(); }
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) { proxy.postInit(); }
}
