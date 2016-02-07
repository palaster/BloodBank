package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class BlockMod extends Block {

	public BlockMod(Material p_i45394_1_) {
		super(p_i45394_1_);
		setCreativeTab(CreativeTabBB.tabSS);
		setHardness(3F);
		setHarvestLevel("pickaxe", 0);
		setStepSound(soundTypeStone);
	}
	
	@Override
	public Block setUnlocalizedName(String name) {
		GameRegistry.registerBlock(this, name);
		return super.setUnlocalizedName(name);
	}

	@SideOnly(Side.CLIENT)
	public void setBlockRender(String name) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
