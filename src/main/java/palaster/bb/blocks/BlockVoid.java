package palaster.bb.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.blocks.tile.TileEntityVoid;

public class BlockVoid extends BlockModContainer {

	public BlockVoid(Material p_i45394_1_) {
		super(p_i45394_1_);
		setUnlocalizedName("void");
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) { return new AxisAlignedBB(0.01D, 0.01D, 0.01D, 0.99D, 0.99D, 0.99D); }
	
	@Override
	public boolean isFullCube(IBlockState state) { return false; }
	
	@Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(!worldIn.isRemote)
			if(entityIn instanceof EntityLivingBase) {
				entityIn.attackEntityFrom(DamageSource.outOfWorld, 6f);
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
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) { return Item.getItemFromBlock(Blocks.AIR); }
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) { return new TileEntityVoid(); }
}
