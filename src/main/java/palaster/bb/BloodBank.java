package palaster.bb;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.core.proxy.CommonProxy;
import palaster.bb.libs.LibMod;

@Mod(modid = LibMod.modid, name = LibMod.name, version = LibMod.version, dependencies = LibMod.dependencies, guiFactory = LibMod.guiFactory)
public class BloodBank {
	
	@Instance(LibMod.modid)
	public static BloodBank instance;
	
	@SidedProxy(clientSide = LibMod.client, serverSide = LibMod.server)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		BBEventHandler.init(e.getSuggestedConfigurationFile());
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) { proxy.init(); }
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) { proxy.postInit(); }
}
