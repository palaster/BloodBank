package palaster97.ss.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class SSItems {
	
	public static Item mobSouls;
	public static Item atmanSword;
	public static Item soulBinder;
	public static Item worldSoulBinder;
	public static Item journal;
	
	public static Item recordUnlight;
	
	public static void init() {
		mobSouls = new ItemMobSoul();
		atmanSword = new ItemAtmanSword(EnumHelper.addToolMaterial("atman", 2, -1, 6.0f, 2.0f, 0));
		soulBinder = new ItemSoulBinder();
		worldSoulBinder = new ItemWorldSoulBinder();
		journal = new ItemJournal();

		recordUnlight = new ItemModRecord("unlight");
	}
}