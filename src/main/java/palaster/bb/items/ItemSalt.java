package palaster.bb.items;

import net.minecraftforge.common.MinecraftForge;

public class ItemSalt extends ItemMod {
	
	public ItemSalt(String unlocalizedName) {
		super(unlocalizedName);
		MinecraftForge.EVENT_BUS.register(this);
	}
}
