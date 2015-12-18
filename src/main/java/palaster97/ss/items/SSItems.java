package palaster97.ss.items;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import palaster97.ss.libs.LibMod;

public class SSItems {
	
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
	deathBook,
	athame,
	bloodBook;
	
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
		deathBook = new ItemBookDeath();
		athame = new ItemAthame();
		bloodBook = new ItemBookBlood();
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		ssResources = new ItemSSResources();

		ModelBakery.addVariantName(ssResources, LibMod.modid + ":vHeart", LibMod.modid + ":test");
	}
}