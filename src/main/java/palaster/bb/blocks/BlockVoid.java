package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.blocks.tile.TileEntityVoid;

public class BlockVoid extends BlockModContainer {

	public BlockVoid(Material p_i45394_1_) {
		super(p_i45394_1_);
		setUnlocalizedName("void");
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(!worldIn.isRemote)
			if(entityIn instanceof EntityLivingBase) {
				entityIn.attackEntityFrom(DamageSource.outOfWorld, 1f);
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 60));
				if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityVoid && ((TileEntityVoid) worldIn.getTileEntity(pos)).getOriginalBlock() != null)
					worldIn.setBlockState(pos, ((TileEntityVoid) worldIn.getTileEntity(pos)).getOriginalBlock().getDefaultState());
			}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityVoid) {
				Block ogBlock = ((TileEntityVoid) worldIn.getTileEntity(pos)).getOriginalBlock();
				if(ogBlock != null) {
					super.breakBlock(worldIn, pos, state);
					worldIn.setBlockState(pos, ogBlock.getDefaultState());
				}
			} else
				super.breakBlock(worldIn, pos, state);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityVoid(); }
}
