package palaster.bb.libs;

public class LibMod {

	public static final String modid = "bb";
	public static final String name = "Blood Bank";
	public static final String version = "@VERSION@";
	public static final String dependencies = "after:JEI";
	public static final String guiFactory = "palaster.bb.client.gui.BBGuiFactory";
	public static final String updateJSON = "https://raw.githubusercontent.com/palaster/BloodBank/HEAD/version/update.json";
	
	public static final String client = "palaster.bb.core.proxy.ClientProxy";
	public static final String server = "palaster.bb.core.proxy.CommonProxy";
}
