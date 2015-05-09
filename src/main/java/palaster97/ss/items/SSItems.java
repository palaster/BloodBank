package palaster97.ss.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class SSItems {
	
	public static Item souls;
	public static Item atmanSword;
	public static Item soulBinder;
	public static Item worldSoulBinder;
	public static Item journal;
	public static Item staffSkeleton;
	public static Item staffEfreet;
	public static Item staffTime;
	public static Item staffVoidWalker;
	public static Item magicBow;
	public static Item coreFuel;
	public static Item animalHerder;
	public static Item inscriptionKit;
	
	public static Item debug;
	public static Item yinYang;
	
	public static void init() {
		souls = new ItemSoul();
		atmanSword = new ItemAtmanSword(EnumHelper.addToolMaterial("atman", 2, -1, 6.0f, 2.0f, 0));
		soulBinder = new ItemSoulBinder();
		worldSoulBinder = new ItemWorldSoulBinder();
		journal = new ItemJournal();
		staffSkeleton = new ItemStaffSkeleton();
		staffEfreet = new ItemStaffEfreet();
		staffTime = new ItemStaffTime();
		staffVoidWalker = new ItemStaffVoidWalker();
		magicBow = new ItemMagicBow();
		coreFuel = new ItemCoreFuel();
		animalHerder = new ItemAnimalHerder();
		inscriptionKit = new ItemInscriptionKit();
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
	}
}