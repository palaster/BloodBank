package palaster97.ss.items;

import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.libs.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTrident extends ItemSword implements IDuctTapeable {

	public ItemTrident(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
		setCreativeTab(CreativeTabSS.tabSS);
		setUnlocalizedName("trident");
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		GameRegistry.registerItem(this, unlocalizedName);
		return super.setUnlocalizedName(unlocalizedName);
	}
	
	@SideOnly(Side.CLIENT)
	public void setItemRender(String name) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
