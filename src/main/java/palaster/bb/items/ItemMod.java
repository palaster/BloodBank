package palaster.bb.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class ItemMod extends Item {
	
	public ItemMod() {
		super();
		setCreativeTab(CreativeTabBB.tabSS);
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		setRegistryName(new ResourceLocation(LibMod.modid, unlocalizedName));
		GameRegistry.register(this);
		setCustomModelResourceLocation();
		return super.setUnlocalizedName(LibMod.modid + ":" + unlocalizedName);
	}

	@SideOnly(Side.CLIENT)
	public void setCustomModelResourceLocation() { ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory")); }
}
