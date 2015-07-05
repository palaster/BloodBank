package palaster97.ss.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class SSItems {
	
	public static Item souls, 
	atmanSword,
	soulBinder,
	worldSoulBinder,
	staffSkeleton,
	staffEfreet,
	staffTime,
	staffVoidWalker,
	staffHungryShadows,
	magicBow,
	animalHerder,
	magicDuctTape,
	trident;
	
	public static Item debug,
	yinYang;
	
	public static void init() {
		souls = new ItemSoul();
		atmanSword = new ItemAtmanSword(EnumHelper.addToolMaterial("atman", 2, 1024, 4.0f, 2.0f, 0));
		soulBinder = new ItemSoulBinder();
		worldSoulBinder = new ItemWorldSoulBinder();
		staffSkeleton = new ItemStaffSkeleton();
		staffEfreet = new ItemStaffEfreet();
		staffTime = new ItemStaffTime();
		staffVoidWalker = new ItemStaffVoidWalker();
		staffHungryShadows = new ItemStaffHungryShadows();
		magicBow = new ItemMagicBow();
		animalHerder = new ItemAnimalHerder();
		magicDuctTape = new ItemMagicDuctTape();
		trident = new ItemTrident(EnumHelper.addToolMaterial("trident", 3, 1024, 4.0f, 3.0f, 22));
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
	}
}