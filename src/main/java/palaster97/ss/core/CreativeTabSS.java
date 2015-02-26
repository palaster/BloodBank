package palaster97.ss.core;

import palaster97.ss.blocks.SSBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabSS {

	public static CreativeTabs tabSS;
	
	public static void init() {
		tabSS = new CreativeTabs(CreativeTabs.getNextID(), "tabSS") {
			@Override
			public Item getTabIconItem() { return Item.getItemFromBlock(SSBlocks.soulCompressor); }
		};
	}
}
