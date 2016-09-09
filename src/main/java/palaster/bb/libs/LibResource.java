package palaster.bb.libs;

import net.minecraft.util.ResourceLocation;

public class LibResource {

	private static final String GUI = "textures/gui/";
	private static final String MODELS = "textures/models/";

	public static final ResourceLocation VOID_ANCHOR = new ResourceLocation(LibMod.MODID, GUI + "voidAnchor.png");
	public static final ResourceLocation DESALINATOR = new ResourceLocation(LibMod.MODID, GUI + "desalinator.png");
	public static final ResourceLocation BLANK = new ResourceLocation(LibMod.MODID, GUI + "blank.png");
	
	public static final ResourceLocation BB_HUD = new ResourceLocation(LibMod.MODID, GUI + "bbHUD.png");
	public static final ResourceLocation BB_POTIONS = new ResourceLocation(LibMod.MODID, GUI + "bbPotions.png");

	public static final ResourceLocation BANK_TELLER = new ResourceLocation(LibMod.MODID, MODELS + "demonicBankTeller.png");
	public static final ResourceLocation ITZTLI_TABLET = new ResourceLocation(LibMod.MODID, MODELS + "itztliTablet.png");
	
	public static final ResourceLocation ITZTLI_TABLET_LOOT = new ResourceLocation(LibMod.MODID, "entities/itztiliTablet");
}
