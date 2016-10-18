package palaster.bb.items;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import palaster.bb.libs.LibMod;
import palaster.libpal.items.ItemMod;

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
		whip,
		careerPamphlet,
		howToWitch;

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
		
		staffSkeleton = new ItemStaffSkeleton(createResourceLocation("staffSkeleton"));
		staffEfreet = new ItemStaffEfreet(createResourceLocation("staffEfreet"));
		staffTime = new ItemStaffTime(createResourceLocation("staffTime"));
		staffVoidWalker = new ItemStaffVoidWalker(createResourceLocation("staffVoidWalker"));
		staffHungryShadows = new ItemStaffHungryShadows(createResourceLocation("staffHungryShadows"));
		animalHerder = new ItemAnimalHerder(createResourceLocation("animalHerder"));
		bloodBottle = new ItemBloodBottle(createResourceLocation("bloodBottle"));
		bloodBook = new ItemBookBlood(createResourceLocation("bookBlood"));
		estusFlask = new ItemEstusFlask(createResourceLocation("estusFlask"));
		armorActivator = new ItemArmorActivator(createResourceLocation("armorActivator"));
		resurrectionStone = new ItemResurrectionStone(createResourceLocation("resurrectionStone"));
		ghostWhisper = new ItemGhostWhisper(createResourceLocation("ghostWhisper"));
		token = new ItemToken(createResourceLocation("token"));
		pigDefense = new ItemPigDefense(createResourceLocation("pigDefense"));
		flames = new ItemFlames(createResourceLocation("flames"));
		carthusFlameArc = new ItemCarthusFlameArc(createResourceLocation("carthusFlameArc"));
		sacredFlame = new ItemSacredFlame(createResourceLocation("sacredFlame"));
		leacher = new ItemLeacher(leach); // TODO: Convert
		horn = new ItemHorn(createResourceLocation("horn"));
		talisman = new ItemTalisman(createResourceLocation("talisman"));
		boundPlayer = new ItemBoundPlayer(createResourceLocation("boundPlayer"));
		boundBloodBottle = new ItemBoundBloodBottle(createResourceLocation("boundBloodBottle"));
		rpgIntro = new ItemRPGIntro(createResourceLocation("rpgIntro"));
		clericStaff = new ItemClericStaff(createResourceLocation("clericStaff"));
		purifyingBook = new ItemPurifyingBook(createResourceLocation("purifyingBook"));
		salt = new ItemMod(createResourceLocation("salt"));
		soulCoin = new ItemMod(createResourceLocation("soulCoin"));
		whip = new ItemWhip(createResourceLocation("whip"));
		careerPamphlet = new ItemCareerPamphlet(createResourceLocation("careerPamphlet"));
		howToWitch = new ItemHowToWitch(createResourceLocation("howToWitch"));

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
		
		debug = new ItemDebug(createResourceLocation("debug"));
		yinYang = new ItemMod(createResourceLocation("yinYang"));
		bbResources = new ItemBBResources(createResourceLocation("bbResources"));
		registerOreDictionary();
	}

	public static void registerOreDictionary() { OreDictionary.registerOre("dustSalt", salt); }

	private static final ResourceLocation createResourceLocation(String unlocalizedName) { return new ResourceLocation(LibMod.MODID, unlocalizedName); }
	
	@SideOnly(Side.CLIENT)
	public static void registerCustomModelResourceLocation() {
		ResourceLocation[] rl = new ResourceLocation[ItemBBResources.NAMES.length];
		for(int i = 0; i < ItemBBResources.NAMES.length; i++)
			rl[i] = new ResourceLocation("bb:" + ItemBBResources.NAMES[i]);
		ModelBakery.registerItemVariants(bbResources, rl);
		ItemBBResources.setCustomModelResourceLocation(bbResources);
		for(Item item : Item.REGISTRY)
			if(((item instanceof ItemMod && item.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.MODID)) || item.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.MODID)) && !(item instanceof ItemBBResources))
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}