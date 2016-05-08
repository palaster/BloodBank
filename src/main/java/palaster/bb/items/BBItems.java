package palaster.bb.items;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class BBItems {

	public static ItemArmor.ArmorMaterial bound = EnumHelper.addArmorMaterial("bound", "bound", -1, new int[]{3, 7, 6, 3}, 0, SoundEvents.item_armor_equip_generic);
	
	public static Item staffSkeleton,
	staffEfreet,
	staffTime,
	staffVoidWalker,
	staffHungryShadows,
	animalHerder,
	bloodBottle,
	hephaestusHammer,
	trident,
	bloodBook,
	letter,
	undeadMonitor;

	public static Item flames,
	carthusFlameArc;

	public static ItemArmor
	boundHelmet,
	boundChestplate,
	boundLeggings,
	boundBoots;
	
	public static Item debug,
	yinYang,
	bbResources;
	
	public static void init() {
		staffSkeleton = new ItemStaffSkeleton();
		staffEfreet = new ItemStaffEfreet();
		staffTime = new ItemStaffTime();
		staffVoidWalker = new ItemStaffVoidWalker();
		staffHungryShadows = new ItemStaffHungryShadows();
		animalHerder = new ItemAnimalHerder();
		bloodBottle = new ItemBloodBottle();
		hephaestusHammer = new ItemHephaestusHammer();
		trident = new ItemTrident(EnumHelper.addToolMaterial("trident", 3, -1, 4.0f, 6.0f, 22));
		bloodBook = new ItemBookBlood();
		letter = new ItemLetter();
		undeadMonitor = new ItemUndeadMonitor();

		flames = new ItemFlames();
		carthusFlameArc = new ItemCarthusFlameArc();

		boundHelmet = new BBArmor(bound, 0, EntityEquipmentSlot.HEAD);
		boundChestplate = new BBArmor(bound, 0, EntityEquipmentSlot.CHEST);
		boundLeggings = new BBArmor(bound, 0, EntityEquipmentSlot.LEGS);
		boundBoots = new BBArmor(bound, 0, EntityEquipmentSlot.FEET);
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		bbResources = new ItemBBResources();
	}
}