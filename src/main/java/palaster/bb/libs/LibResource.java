package palaster.bb.libs;

import net.minecraft.util.ResourceLocation;

public class LibResource {

	private static final String gui = "textures/gui/";
	private static final String models = "textures/models/";

	public static final ResourceLocation voidAnchor = new ResourceLocation(LibMod.modid, gui + "voidAnchor.png");
	public static final ResourceLocation letter = new ResourceLocation(LibMod.modid, gui + "letter.png");
	public static final ResourceLocation undeadMonitor = new ResourceLocation(LibMod.modid, gui + "undeadMonitor.png");
	public static final ResourceLocation row = new ResourceLocation(LibMod.modid, gui + "row.png");
	
	public static final ResourceLocation bbHUD = new ResourceLocation(LibMod.modid, gui + "bbHUD.png");

	public static final ResourceLocation bankTeller = new ResourceLocation(LibMod.modid, models + "demonicBankTeller.png");
	public static final ResourceLocation itztiliTablet = new ResourceLocation(LibMod.modid, models + "itztliTablet.png");
}
