package palaster97.ss.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class SSItems {
	
	public static Item mobSouls;
	public static Item atmanSword;
	public static Item voidSword;
	public static Item soulBinder;
	public static Item worldSoulBinder;
	public static Item journal;
	public static Item staffSkeleton;
	public static Item staffEfreet;
	public static Item staffTime;
	public static Item staffVoidWalker;
	public static Item magicBow;
	public static Item gemTransposition;
	public static Item coreFuel;
	public static Item animalHerder;
	public static Item inscriptionKit;
	
	public static Item recordUnlight;
	
	public static Item debug;
	
	public static void init() {
		mobSouls = new ItemMobSoul();
		atmanSword = new ItemAtmanSword(EnumHelper.addToolMaterial("atman", 2, -1, 6.0f, 2.0f, 0));
		voidSword = new ItemVoidSword(EnumHelper.addToolMaterial("void", 2, 1561, 6.0f, 2.0f, 14));
		soulBinder = new ItemSoulBinder();
		worldSoulBinder = new ItemWorldSoulBinder();
		journal = new ItemJournal();
		staffSkeleton = new ItemStaffSkeleton();
		staffEfreet = new ItemStaffEfreet();
		staffTime = new ItemStaffTime();
		staffVoidWalker = new ItemStaffVoidWalker();
		magicBow = new ItemMagicBow();
		gemTransposition = new ItemGemTransposition();
		coreFuel = new ItemCoreFuel();
		animalHerder = new ItemAnimalHerder();
		inscriptionKit = new ItemInscriptionKit();

		recordUnlight = new ItemModRecord("unlight");
		
		debug = new ItemDebug();
	}
}