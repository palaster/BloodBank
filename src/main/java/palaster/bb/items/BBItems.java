package palaster.bb.items;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import palaster.bb.libs.LibMod;

public class BBItems {

	public static ItemArmor.ArmorMaterial bound = EnumHelper.addArmorMaterial("bound", "bound", -1, new int[]{3, 7, 6, 3}, 0);
	
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
	bloodBook;

	public static ItemArmor
	boundHelmet,
	boundChestplate,
	boundLeggings,
	boundBoots;
	
	public static Item debug,
	yinYang,
	ssResources;
	
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

		boundHelmet = new BBArmor(bound, 0,0);
		boundChestplate = new BBArmor(bound, 0,1);
		boundLeggings = new BBArmor(bound, 0,2);
		boundBoots = new BBArmor(bound, 0,3);
		
		debug = new ItemDebug();
		yinYang = new ItemYinYang();
		ssResources = new ItemSSResources();

		ModelBakery.registerItemVariants(ssResources, new ResourceLocation(LibMod.modid + ":bankContract"), new ResourceLocation(LibMod.modid + ":bankID"));
	}
}