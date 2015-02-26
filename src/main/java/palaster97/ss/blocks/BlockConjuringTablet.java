package palaster97.ss.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.blocks.tile.TileEntityConjuringTablet;

public class BlockConjuringTablet extends BlockModContainer {

	public BlockConjuringTablet(Material p_i45394_1_) {
		super(p_i45394_1_);
		setBlockBounds(0, 0, 0, 1, .625f, 1);
		setUnlocalizedName("conjuringTablet");
	}
	
	@Override
	public boolean isFullCube() { return false; }
	
	@Override
	public int getRenderType() { return 3; }
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			playerIn.openGui(ScreamingSouls.instance, 3, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityConjuringTablet(); }
}
