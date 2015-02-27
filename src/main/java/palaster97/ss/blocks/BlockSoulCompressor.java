package palaster97.ss.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.ScreamingSouls;

public class BlockSoulCompressor extends BlockMod {

	public BlockSoulCompressor(Material p_i45394_1_) {
		super(p_i45394_1_);
		setUnlocalizedName("soulCompressor");
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			playerIn.openGui(ScreamingSouls.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}