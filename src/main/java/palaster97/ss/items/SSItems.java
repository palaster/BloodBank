package palaster97.ss.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import palaster97.ss.libs.LibMod;

public class SSItems {

	public static ItemArmor.ArmorMaterial arsonist = EnumHelper.addArmorMaterial("arsonist", LibMod.modid + ":arsonist", 15, new int[]{2, 6, 5, 2}, 9);
	
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

	public static ItemArmor arsonistHood,
	arsonistTunic,
	arsonistPants,
	arsonistShoes;
	
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

		arsonistHood = new ItemArsoninstGarments(arsonist, 0, 0);
		arsonistTunic = new ItemArsoninstGarments(arsonist, 0, 1);
		arsonistPants = new ItemArsoninstGarments(arsonist, 0, 2);
		arsonistShoes = new ItemArsoninstGarments(arsonist, 0, 3);
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
	}
}