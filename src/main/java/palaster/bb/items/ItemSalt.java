package palaster.bb.items;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemSalt extends ItemMod {

	public static final String TAG_BOOLEAN_PURIFIED = "IsPurified";
	
	public ItemSalt() {
		super();
		setUnlocalizedName("salt");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null && e.getItemStack().hasTagCompound())
			if(e.getItemStack().getTagCompound().getBoolean(TAG_BOOLEAN_PURIFIED))
				e.getToolTip().add(I18n.format("bb.misc.purified"));
	}
}
