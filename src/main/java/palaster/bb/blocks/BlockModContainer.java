package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public abstract class BlockModContainer extends BlockContainer {

	public BlockModContainer(Material p_i45394_1_) {
		super(p_i45394_1_);
		setCreativeTab(CreativeTabBB.tabBB);
		setHardness(3F);
		setHarvestLevel("pickaxe", 0);
	}
	
	@Override
	public Block setUnlocalizedName(String name) {
		setRegistryName(new ResourceLocation(LibMod.modid, name));
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(getRegistryName()));
		setCustomModelResourceLocation();
		return super.setUnlocalizedName(LibMod.modid + ":" + name);
	}

	@SideOnly(Side.CLIENT)
	public void setCustomModelResourceLocation() { ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory")); }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) { return EnumBlockRenderType.MODEL; }

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te instanceof IInventory)
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
		super.breakBlock(worldIn, pos, state);
	}
}
