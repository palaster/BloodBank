package palaster.bb.items;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class BBItems {

	public static ItemArmor.ArmorMaterial bound = EnumHelper.addArmorMaterial("bound", "bound", -1, new int[]{3, 7, 6, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f);
	
	public static Item staffSkeleton,
	staffEfreet,
	staffTime,
	staffVoidWalker,
	staffHungryShadows,
	animalHerder,
	bloodBottle,
	bloodBook,
	letter,
	undeadMonitor,
	estusFlask,
	ashenEstusFlask;

	public static Item flames,
	carthusFlameArc,
	sacredFlame;

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
		bloodBook = new ItemBookBlood();
		letter = new ItemLetter();
		undeadMonitor = new ItemUndeadMonitor();
		estusFlask = new ItemEstusFlask();
		ashenEstusFlask = new ItemAshenEstusFlask();

		flames = new ItemFlames();
		carthusFlameArc = new ItemCarthusFlameArc();
		sacredFlame = new ItemSacredFlame();

		boundHelmet = new BBArmor(bound, 0, EntityEquipmentSlot.HEAD);
		boundChestplate = new BBArmor(bound, 0, EntityEquipmentSlot.CHEST);
		boundLeggings = new BBArmor(bound, 0, EntityEquipmentSlot.LEGS);
		boundBoots = new BBArmor(bound, 0, EntityEquipmentSlot.FEET);
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		bbResources = new ItemBBResources();
	}
}