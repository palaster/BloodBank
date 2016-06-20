package palaster.bb.items;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class BBItems {

	public static ItemArmor.ArmorMaterial bound = EnumHelper.addArmorMaterial("bound", "bound", -1, new int[]{3, 7, 6, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f);
	public static ItemArmor.ArmorMaterial sand = EnumHelper.addArmorMaterial("sand", "sand", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	
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
	ashenEstusFlask,
	armorActivator,
	resurrectionStone,
	ghostWhisper;

	public static Item flames,
	carthusFlameArc,
	sacredFlame;

	public static ItemArmor
	boundHelmet,
	boundChestplate,
	boundLeggings,
	boundBoots,
	sandHelmet,
	sandChestplate,
	sandLeggings,
	sandBoots;
	
	public static Item debug,
	yinYang,
	bbResources;
	
	public static void init() {
		sand.customCraftingMaterial = Item.getItemFromBlock(Blocks.SAND);
		
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
		armorActivator = new ItemArmorActivator();
		resurrectionStone = new ItemResurrectionStone();
		ghostWhisper = new ItemGhostWhisper();

		flames = new ItemFlames();
		carthusFlameArc = new ItemCarthusFlameArc();
		sacredFlame = new ItemSacredFlame();

		boundHelmet = new BoundArmor(bound, 0, EntityEquipmentSlot.HEAD);
		boundChestplate = new BoundArmor(bound, 0, EntityEquipmentSlot.CHEST);
		boundLeggings = new BoundArmor(bound, 0, EntityEquipmentSlot.LEGS);
		boundBoots = new BoundArmor(bound, 0, EntityEquipmentSlot.FEET);
		sandHelmet = new SandArmor(sand, 0, EntityEquipmentSlot.HEAD);
		sandChestplate = new SandArmor(sand, 0, EntityEquipmentSlot.CHEST);
		sandLeggings = new SandArmor(sand, 0, EntityEquipmentSlot.LEGS);
		sandBoots = new SandArmor(sand, 0, EntityEquipmentSlot.FEET);
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		bbResources = new ItemBBResources();
	}
	
	public static void registerCustomModelResourceLocation() {
		ItemMod.setCustomModelResourceLocation(staffSkeleton);
		ItemMod.setCustomModelResourceLocation(staffEfreet);
		ItemMod.setCustomModelResourceLocation(staffTime);
		ItemMod.setCustomModelResourceLocation(staffVoidWalker);
		ItemMod.setCustomModelResourceLocation(staffHungryShadows);
		ItemMod.setCustomModelResourceLocation(animalHerder);
		ItemMod.setCustomModelResourceLocation(bloodBottle);
		ItemMod.setCustomModelResourceLocation(letter);
		ItemMod.setCustomModelResourceLocation(undeadMonitor);
		ItemMod.setCustomModelResourceLocation(estusFlask);
		ItemMod.setCustomModelResourceLocation(ashenEstusFlask);
		ItemMod.setCustomModelResourceLocation(armorActivator);
		ItemMod.setCustomModelResourceLocation(resurrectionStone);
		ItemMod.setCustomModelResourceLocation(ghostWhisper);
		
		ItemMod.setCustomModelResourceLocation(flames);
		ItemMod.setCustomModelResourceLocation(carthusFlameArc);
		ItemMod.setCustomModelResourceLocation(sacredFlame);
		
		ItemMod.setCustomModelResourceLocation(boundHelmet);
		ItemMod.setCustomModelResourceLocation(boundChestplate);
		ItemMod.setCustomModelResourceLocation(boundLeggings);
		ItemMod.setCustomModelResourceLocation(boundBoots);
		ItemMod.setCustomModelResourceLocation(sandHelmet);
		ItemMod.setCustomModelResourceLocation(sandChestplate);
		ItemMod.setCustomModelResourceLocation(sandLeggings);
		ItemMod.setCustomModelResourceLocation(sandBoots);
		
		ItemMod.setCustomModelResourceLocation(debug);
		ItemMod.setCustomModelResourceLocation(yinYang);
		ItemBBResources.setCustomModelResourceLocation(bbResources);
	}
}