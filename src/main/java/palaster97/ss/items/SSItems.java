package palaster97.ss.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

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
	deathBook;
	
	public static Item debug,
	yinYang;
	
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
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
	}
}