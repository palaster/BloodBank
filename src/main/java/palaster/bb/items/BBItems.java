package palaster.bb.items;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import palaster.bb.libs.LibMod;

public class BBItems {

	public static ItemArmor.ArmorMaterial genericUnbreakableDiamond = EnumHelper.addArmorMaterial("genericUnbreakableDiamond", "genericUnbreakableDiamond", -1, new int[]{3, 6, 8, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f);
	public static ItemArmor.ArmorMaterial sand = EnumHelper.addArmorMaterial("sand", "sand", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ItemArmor.ToolMaterial leach = EnumHelper.addToolMaterial("leach", 3, -1, 8.0F, 3.0F, 0);
	
	public static Item staffSkeleton,
	staffEfreet,
	staffTime,
	staffVoidWalker,
	staffHungryShadows,
	animalHerder,
	bloodBottle,
	bloodBook,
	undeadMonitor,
	estusFlask,
	armorActivator,
	resurrectionStone,
	ghostWhisper,
	token,
	pigDefense,
	flames,
	carthusFlameArc,
	sacredFlame,
	leacher,
	horn,
	talisman,
	boundPlayer,
	boundBloodBottle,
	careerPamphlet,
	apothecaryIntro;

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
		undeadMonitor = new ItemUndeadMonitor();
		estusFlask = new ItemEstusFlask();
		armorActivator = new ItemArmorActivator();
		resurrectionStone = new ItemResurrectionStone();
		ghostWhisper = new ItemGhostWhisper();
		token = new ItemToken();
		pigDefense = new ItemPigDefense();
		flames = new ItemFlames();
		carthusFlameArc = new ItemCarthusFlameArc();
		sacredFlame = new ItemSacredFlame();
		leacher = new ItemLeacher(leach);
		horn = new ItemHorn();
		talisman = new ItemTalisman();
		boundPlayer = new ItemBoundPlayer();
		boundBloodBottle = new ItemBoundBloodBottle();
		careerPamphlet = new ItemCareerPamphlet();
		apothecaryIntro = new ItemApothecaryIntro();

		boundHelmet = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.HEAD);
		boundChestplate = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.CHEST);
		boundLeggings = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.LEGS);
		boundBoots = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.FEET);
		sandHelmet = new ItemSandArmor(sand, 0, EntityEquipmentSlot.HEAD);
		sandChestplate = new ItemSandArmor(sand, 0, EntityEquipmentSlot.CHEST);
		sandLeggings = new ItemSandArmor(sand, 0, EntityEquipmentSlot.LEGS);
		sandBoots = new ItemSandArmor(sand, 0, EntityEquipmentSlot.FEET);
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		bbResources = new ItemBBResources();
	}
	
	public static void registerCustomModelResourceLocation() {
		for(Item item : Item.REGISTRY)
			if(item.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.modid))
				ItemMod.setCustomModelResourceLocation(item);
	}
}