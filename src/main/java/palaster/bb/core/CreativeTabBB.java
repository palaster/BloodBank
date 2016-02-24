package palaster.bb.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import palaster.bb.blocks.BBBlocks;

public class CreativeTabBB {

	public static CreativeTabs tabSS;
	
	public static void init() {
		tabSS = new CreativeTabs(CreativeTabs.getNextID(), "tabBB") {
			@Override
			public Item getTabIconItem() { return Item.getItemFromBlock(BBBlocks.playerManipulator); }
		};
	}
}
