package palaster.bb.items;

import net.minecraftforge.common.MinecraftForge;

public class ItemSalt extends ItemMod {
	
	public ItemSalt() {
		super();
		setUnlocalizedName("salt");
		MinecraftForge.EVENT_BUS.register(this);
	}
}
