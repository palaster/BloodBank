package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class BlockMod extends Block {

	public BlockMod(String unlocalizedName, Material material) {
		super(material);
		setCreativeTab(CreativeTabBB.tabBB);
		setHardness(3F);
		setHarvestLevel("pickaxe", 0);
		setUnlocalizedName(unlocalizedName);
	}
	
	@Override
	public Block setUnlocalizedName(String name) {
		setRegistryName(new ResourceLocation(LibMod.MODID, name));
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(getRegistryName()));
		return super.setUnlocalizedName(LibMod.MODID + ":" + name);
	}

	@SideOnly(Side.CLIENT)
	public static void setCustomModelResourceLocation(Block block) { ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory")); }
}