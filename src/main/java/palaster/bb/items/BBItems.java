package palaster.bb.items;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class BBItems {

	public static ItemArmor.ArmorMaterial bound = EnumHelper.addArmorMaterial("bound", "bound", -1, new int[]{3, 7, 6, 3}, 0, SoundEvents.item_armor_equip_generic);
	
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
	letter;

	public static ItemArmor
	boundHelmet,
	boundChestplate,
	boundLeggings,
	boundBoots;
	
	public static Item debug,
	yinYang,
	bbResources;
	
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
		letter = new ItemLetter();

		boundHelmet = new BBArmor(bound, 0, EntityEquipmentSlot.HEAD);
		boundChestplate = new BBArmor(bound, 0, EntityEquipmentSlot.CHEST);
		boundLeggings = new BBArmor(bound, 0, EntityEquipmentSlot.LEGS);
		boundBoots = new BBArmor(bound, 0, EntityEquipmentSlot.FEET);
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		bbResources = new ItemBBResources();
	}
}