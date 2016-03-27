package palaster.bb.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class ItemMod extends Item {
	
	public ItemMod() {
		super();
		setCreativeTab(CreativeTabBB.tabSS);
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		GameRegistry.registerItem(this, unlocalizedName);
		return super.setUnlocalizedName(unlocalizedName);
	}
	
	@SideOnly(Side.CLIENT)
	public void setItemRender(String name) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
