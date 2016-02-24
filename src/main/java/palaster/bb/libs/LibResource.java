package palaster.bb.libs;

import net.minecraft.util.ResourceLocation;

public class LibResource {

	private static final String gui = "textures/gui/";
	private static final String models = "textures/models/";

	public static final ResourceLocation playerManipulator_0 = new ResourceLocation(LibMod.modid, gui + "playerManipulator_0.png");
	public static final ResourceLocation playerManipulator_1 = new ResourceLocation(LibMod.modid, gui + "playerManipulator_1.png");
	public static final ResourceLocation playerManipulator_2 = new ResourceLocation(LibMod.modid, gui + "playerManipulator_2.png");
	public static final ResourceLocation voidAnchor = new ResourceLocation(LibMod.modid, gui + "voidAnchor.png");
	public static final ResourceLocation letter = new ResourceLocation(LibMod.modid, gui + "letter.png");
	public static final ResourceLocation row = new ResourceLocation(LibMod.modid, gui + "row.png");
	
	public static final ResourceLocation bbHUD = new ResourceLocation(LibMod.modid, gui + "bbHUD.png");

	public static final ResourceLocation bankTeller = new ResourceLocation(LibMod.modid, models + "demonicBankTeller.png");
}
