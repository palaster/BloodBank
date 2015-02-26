package palaster97.ss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;

public class BlockPlayerSoulManipulator extends BlockModContainer {

	public BlockPlayerSoulManipulator(Material p_i45394_1_) {
		super(p_i45394_1_);
		setUnlocalizedName("playerSoulManipulator");
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			playerIn.openGui(ScreamingSouls.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) { return new TileEntityPlayerSoulManipulator(); }
}
