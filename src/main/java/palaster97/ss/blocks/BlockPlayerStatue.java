package palaster97.ss.blocks;

import palaster97.ss.ScreamingSouls;
import palaster97.ss.blocks.tile.TileEntityPlayerStatue;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPlayerStatue extends BlockModContainer {

	public BlockPlayerStatue(Material p_i45394_1_) {
		super(p_i45394_1_);
		setUnlocalizedName("playerStatue");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(playerIn.isSneaking())
				playerIn.openGui(ScreamingSouls.instance, 4, worldIn, pos.getX(), pos.getY(), pos.getZ());
			else
				playerIn.openGui(ScreamingSouls.instance, 5, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityPlayerStatue(); }
}
