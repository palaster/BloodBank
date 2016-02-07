package palaster.bb.items;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import palaster.bb.libs.LibMod;

public class BBItems {
	
	public static Item playerBinder,
	worldBinder,
	staffSkeleton,
	staffEfreet,
	staffTime,
	staffVoidWalker,
	staffHungryShadows,
	animalHerder,
	magicDuctTape,
	tapeHeart,
	hephaestusHammer,
	trident,
	athame,
	bloodBook,
	bloodPact;
	
	public static Item debug,
	yinYang,
	ssResources;
	
	public static void init() {
		playerBinder = new ItemPlayerBinder();
		worldBinder = new ItemWorldBinder();
		staffSkeleton = new ItemStaffSkeleton();
		staffEfreet = new ItemStaffEfreet();
		staffTime = new ItemStaffTime();
		staffVoidWalker = new ItemStaffVoidWalker();
		staffHungryShadows = new ItemStaffHungryShadows();
		animalHerder = new ItemAnimalHerder();
		magicDuctTape = new ItemMagicDuctTape();
		tapeHeart = new ItemTapeHeart();
		hephaestusHammer = new ItemHephaestusHammer();
		trident = new ItemTrident(EnumHelper.addToolMaterial("trident", 3, 1024, 4.0f, 3.0f, 22));
		athame = new ItemAthame();
		bloodBook = new ItemBookBlood();
		bloodPact = new ItemBloodPact();
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		ssResources = new ItemSSResources();

		ModelBakery.registerItemVariants(ssResources, new ResourceLocation(LibMod.modid + ":bankContract"), new ResourceLocation(LibMod.modid + ":bankID"));
	}
}