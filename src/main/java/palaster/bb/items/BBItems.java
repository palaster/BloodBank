package palaster.bb.items;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import palaster.bb.libs.LibMod;

public class BBItems {

	public static ItemArmor.ArmorMaterial genericUnbreakableDiamond = EnumHelper.addArmorMaterial("genericUnbreakableDiamond", "genericUnbreakableDiamond", -1, new int[]{3, 6, 8, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f),
			sand = EnumHelper.addArmorMaterial("sand", "sand", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ItemArmor.ToolMaterial leach = EnumHelper.addToolMaterial("leach", 3, -1, 8.0F, 3.0F, 0);
	
	public static Item staffSkeleton,
	staffEfreet,
	staffTime,
	staffVoidWalker,
	staffHungryShadows,
	animalHerder,
	bloodBottle,
	bloodBook,
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
	rpgIntro,
	clericStaff,
	purifyingBook,
	salt,
	soulCoin,
	whip;

	public static ItemArmor
	boundHelmet,
	boundChestplate,
	boundLeggings,
	boundBoots,
	sandHelmet,
	sandChestplate,
	sandLeggings,
	sandBoots,
	sunHelmet,
	sunChestplate,
	sunLeggings,
	sunBoots;
	
	public static Item debug,
	yinYang,
	bbResources;
	
	public static void init() {
		sand.customCraftingMaterial = Item.getItemFromBlock(Blocks.SAND);
		
		staffSkeleton = new ItemStaffSkeleton("staffSkeleton");
		staffEfreet = new ItemStaffEfreet("staffEfreet");
		staffTime = new ItemStaffTime("staffTime");
		staffVoidWalker = new ItemStaffVoidWalker("staffVoidWalker");
		staffHungryShadows = new ItemStaffHungryShadows("staffHungryShadows");
		animalHerder = new ItemAnimalHerder("animalHerder");
		bloodBottle = new ItemBloodBottle("bloodBottle");
		bloodBook = new ItemBookBlood("bookBlood");
		estusFlask = new ItemEstusFlask("estusFlask");
		armorActivator = new ItemArmorActivator("armorActivator");
		resurrectionStone = new ItemResurrectionStone("resurrectionStone");
		ghostWhisper = new ItemGhostWhisper("ghostWhisper");
		token = new ItemToken("token");
		pigDefense = new ItemPigDefense("pigDefense");
		flames = new ItemFlames("flames");
		carthusFlameArc = new ItemCarthusFlameArc("carthusFlameArc");
		sacredFlame = new ItemSacredFlame("sacredFlame");
		leacher = new ItemLeacher(leach);
		horn = new ItemHorn("horn");
		talisman = new ItemTalisman("talisman");
		boundPlayer = new ItemBoundPlayer("boundPlayer");
		boundBloodBottle = new ItemBoundBloodBottle("boundBloodBottle");
		rpgIntro = new ItemRPGIntro("rpgIntro");
		clericStaff = new ItemClericStaff("clericStaff");
		purifyingBook = new ItemPurifyingBook("purifyingBook");
		salt = new ItemSalt("salt");
		soulCoin = new ItemMod("soulCoin");
		whip = new ItemWhip("whip");

		boundHelmet = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.HEAD);
		boundChestplate = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.CHEST);
		boundLeggings = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.LEGS);
		boundBoots = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.FEET);
		sandHelmet = new ItemSandArmor(sand, 0, EntityEquipmentSlot.HEAD);
		sandChestplate = new ItemSandArmor(sand, 0, EntityEquipmentSlot.CHEST);
		sandLeggings = new ItemSandArmor(sand, 0, EntityEquipmentSlot.LEGS);
		sandBoots = new ItemSandArmor(sand, 0, EntityEquipmentSlot.FEET);
		sunHelmet = new ItemSunArmor(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.HEAD);
		sunChestplate = new ItemSunArmor(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.CHEST);
		sunLeggings = new ItemSunArmor(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.LEGS);
		sunBoots = new ItemSunArmor(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.FEET);
		
		debug = new ItemDebug("debug");
		yinYang = new ItemYinYang("yinYang");
		bbResources = new ItemBBResources("bbResources");
		
		registerOreDictionary();
	}
	
	public static void registerOreDictionary() { OreDictionary.registerOre("dustSalt", salt); }
	
	public static void registerCustomModelResourceLocation() {
		ResourceLocation[] rl = new ResourceLocation[ItemBBResources.NAMES.length];
		for(int i = 0; i < ItemBBResources.NAMES.length; i++)
			rl[i] = new ResourceLocation("bb:" + ItemBBResources.NAMES[i]);
		ModelBakery.registerItemVariants(bbResources, rl);
		
		for(Item item : Item.REGISTRY)
			if(item.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.MODID) && !(item instanceof ItemBBResources))
				ItemMod.setCustomModelResourceLocation(item);
		ItemBBResources.setCustomModelResourceLocation(bbResources);
	}
}