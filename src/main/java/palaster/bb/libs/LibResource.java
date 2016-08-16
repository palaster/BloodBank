package palaster.bb.libs;

import net.minecraft.util.ResourceLocation;

public class LibResource {

	private static final String gui = "textures/gui/";
	private static final String models = "textures/models/";

	public static final ResourceLocation voidAnchor = new ResourceLocation(LibMod.modid, gui + "voidAnchor.png");
	public static final ResourceLocation blank = new ResourceLocation(LibMod.modid, gui + "blank.png");
	
	public static final ResourceLocation bbHUD = new ResourceLocation(LibMod.modid, gui + "bbHUD.png");
	public static final ResourceLocation bbPotions = new ResourceLocation(LibMod.modid, gui + "bbPotions.png");

	public static final ResourceLocation bankTeller = new ResourceLocation(LibMod.modid, models + "demonicBankTeller.png");
	public static final ResourceLocation itztiliTablet = new ResourceLocation(LibMod.modid, models + "itztliTablet.png");
	
	public static final ResourceLocation itztiliTabletLoot = new ResourceLocation(LibMod.modid, "entities/itztiliTablet");
}
