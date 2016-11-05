package palaster.bb.libs;

public class LibMod {

	public static final String MODID = "bb";
	public static final String NAME = "Blood Bank";
	public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES = "required-after:libpal";
	public static final String GUI_FACTORY = "palaster.bb.client.gui.BBGuiFactory";
	public static final String UPDATE_JSON = "https://raw.githubusercontent.com/palaster/BloodBank/HEAD/version/update.json";
	
	public static final String CLIENT = "palaster.bb.core.proxy.ClientProxy";
	public static final String SERVER = "palaster.bb.core.proxy.CommonProxy";
}
