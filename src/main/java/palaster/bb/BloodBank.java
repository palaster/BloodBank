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

@Mod(modid = LibMod.MODID, name = LibMod.NAME, version = LibMod.VERSION, dependencies = LibMod.DEPENDENCIES, guiFactory = LibMod.GUI_FACTORY, updateJSON = LibMod.UPDATE_JSON)
public class BloodBank {
	
	@Instance(LibMod.MODID)
	public static BloodBank instance;
	
	@SidedProxy(clientSide = LibMod.CLIENT, serverSide = LibMod.SERVER)
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
