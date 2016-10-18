package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.blocks.tile.TileEntityVoidAnchor;
import palaster.libpal.blocks.BlockModContainer;

public class BlockVoidAnchor extends BlockModContainer {

	public BlockVoidAnchor(ResourceLocation rl, Material p_i45394_1_) { super(rl, p_i45394_1_); }
	
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) { return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D); }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			playerIn.openGui(BloodBank.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) { return new TileEntityVoidAnchor(); }
}
