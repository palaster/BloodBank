package palaster97.ss.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.libs.LibMod;

public class ItemAtmanSword extends ItemSword {

	public ItemAtmanSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
		setCreativeTab(CreativeTabSS.tabSS);
		setUnlocalizedName("atmanSword");
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		GameRegistry.registerItem(this, unlocalizedName, LibMod.modid);
		setItemRender(unlocalizedName);
		return super.setUnlocalizedName(unlocalizedName);
	}
	
	@SideOnly(Side.CLIENT)
	public void setItemRender(String name) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
