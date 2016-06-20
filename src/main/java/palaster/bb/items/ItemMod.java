package palaster.bb.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class ItemMod extends Item {
	
	public ItemMod() {
		super();
		setCreativeTab(CreativeTabBB.tabBB);
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		setRegistryName(new ResourceLocation(LibMod.modid, unlocalizedName));
		GameRegistry.register(this);
		return super.setUnlocalizedName(LibMod.modid + ":" + unlocalizedName);
	}

	@SideOnly(Side.CLIENT)
	public static void setCustomModelResourceLocation(Item item) { ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory")); }
}
