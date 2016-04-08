package palaster.bb.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockModContainer extends BlockMod implements ITileEntityProvider {

	public BlockModContainer(Material p_i45394_1_) {
		super(p_i45394_1_);
		isBlockContainer = true;
	}
	
//	@Override
//	public int getRenderType() { return -1; }

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && te instanceof IInventory)
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

    @Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
    	super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
    	TileEntity tileentity = worldIn.getTileEntity(pos);
    	return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }
}
