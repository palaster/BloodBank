package palaster.bb.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockModContainer extends BlockMod implements ITileEntityProvider {

	public BlockModContainer(Material p_i45394_1_) {
		super(p_i45394_1_);
		isBlockContainer = true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te instanceof IInventory)
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
}
