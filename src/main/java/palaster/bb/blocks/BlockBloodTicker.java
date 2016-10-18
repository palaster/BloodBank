package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import palaster.bb.blocks.tile.TileEntityBloodTicker;
import palaster.libpal.blocks.BlockModContainer;

public class BlockBloodTicker extends BlockModContainer {

	public BlockBloodTicker(ResourceLocation rl, Material p_i45394_1_) { super(rl, p_i45394_1_); }
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) { return new TileEntityBloodTicker(); }
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) { return true; }
}
