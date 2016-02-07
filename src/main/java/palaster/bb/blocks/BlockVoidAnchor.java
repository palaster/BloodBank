package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.blocks.tile.TileEntityModInventory;

public class BlockVoidAnchor extends BlockModContainer {

	public BlockVoidAnchor(Material p_i45394_1_) {
		super(p_i45394_1_);
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
		setUnlocalizedName("voidAnchor");
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			playerIn.openGui(BloodBank.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityModInventory(18) {
		@Override
		public String getName() { return "container.voidAnchor"; }
	};}
}
