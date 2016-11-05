package palaster.bb.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import palaster.bb.libs.LibMod;
import palaster.libpal.items.ItemMod;

public class BBItems {

	public static ItemArmor.ArmorMaterial genericUnbreakableDiamond = EnumHelper.addArmorMaterial("genericUnbreakableDiamond", "genericUnbreakableDiamond", -1, new int[]{3, 6, 8, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f);;
	public static ItemArmor.ToolMaterial leach = EnumHelper.addToolMaterial("leach", 3, -1, 8.0F, 3.0F, 0);
	
	public static Item staffSkeleton,
		staffEfreet,
		staffTime,
		staffVoidWalker,
		staffHungryShadows,
		animalHerder,
		bloodBottle,
		bloodBook,
		armorActivator,
		resurrectionStone,
		ghostWhisper,
		token,
		leacher,
		horn,
		talisman,
		boundPlayer,
		boundBloodBottle,
		rpgIntro,
		clericStaff,
		purifyingBook,
		salt,
		whip,
		careerPamphlet,
		wormEater,
		vampireSigil,
		soulGem,
		pinkSlip;

	public static ItemArmor
		boundHelmet,
		boundChestplate,
		boundLeggings,
		boundBoots,
		sunHelmet,
		sunChestplate,
		sunLeggings,
		sunBoots;
	
	public static Item debug,
		yinYang;
	
	public static void init() {
		staffSkeleton = new ItemStaffSkeleton(createResourceLocation("staffSkeleton"));
		staffEfreet = new ItemStaffEfreet(createResourceLocation("staffEfreet"));
		staffTime = new ItemStaffTime(createResourceLocation("staffTime"));
		staffVoidWalker = new ItemStaffVoidWalker(createResourceLocation("staffVoidWalker"));
		staffHungryShadows = new ItemStaffHungryShadows(createResourceLocation("staffHungryShadows"));
		animalHerder = new ItemAnimalHerder(createResourceLocation("animalHerder"));
		bloodBottle = new ItemBloodBottle(createResourceLocation("bloodBottle"));
		bloodBook = new ItemBookBlood(createResourceLocation("bookBlood"));
		armorActivator = new ItemArmorActivator(createResourceLocation("armorActivator"));
		resurrectionStone = new ItemResurrectionStone(createResourceLocation("resurrectionStone"));
		ghostWhisper = new ItemGhostWhisper(createResourceLocation("ghostWhisper"));
		token = new ItemToken(createResourceLocation("token"));
		leacher = new ItemLeacher(leach); // TODO: Convert
		horn = new ItemHorn(createResourceLocation("horn"));
		talisman = new ItemTalisman(createResourceLocation("talisman"));
		boundPlayer = new ItemBoundPlayer(createResourceLocation("boundPlayer"));
		boundBloodBottle = new ItemBoundBloodBottle(createResourceLocation("boundBloodBottle"));
		rpgIntro = new ItemRPGIntro(createResourceLocation("rpgIntro"));
		clericStaff = new ItemClericStaff(createResourceLocation("clericStaff"));
		purifyingBook = new ItemPurifyingBook(createResourceLocation("purifyingBook"));
		salt = new ItemMod(createResourceLocation("salt"));
		whip = new ItemWhip(createResourceLocation("whip"));
		careerPamphlet = new ItemCareerPamphlet(createResourceLocation("careerPamphlet"));
		wormEater = new ItemWormEater(createResourceLocation("wormEater"));
		vampireSigil = new ItemVampireSigil(createResourceLocation("vampireSigil"));
		soulGem = new ItemSoulGem(createResourceLocation("soulGem"));
		pinkSlip = new ItemPinkSlip(createResourceLocation("pinkSlip"));

		boundHelmet = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.HEAD);
		boundChestplate = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.CHEST);
		boundLeggings = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.LEGS);
		boundBoots = new ItemBoundArmor(genericUnbreakableDiamond, 0, EntityEquipmentSlot.FEET);
		
		debug = new ItemDebug(createResourceLocation("debug"));
		yinYang = new ItemMod(createResourceLocation("yinYang"));
		registerOreDictionary();
	}

	public static void registerOreDictionary() { OreDictionary.registerOre("dustSalt", salt); }

	private static final ResourceLocation createResourceLocation(String unlocalizedName) { return new ResourceLocation(LibMod.MODID, unlocalizedName); }
	
	@SideOnly(Side.CLIENT)
	public static void registerCustomModelResourceLocation() {
		for(Item item : Item.REGISTRY)
			if(item.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.MODID))
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}