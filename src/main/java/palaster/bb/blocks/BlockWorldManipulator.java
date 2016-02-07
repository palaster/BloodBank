package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import palaster.bb.blocks.tile.TileEntityWorldManipulator;
import palaster.bb.items.ItemWorldBinder;

public class BlockWorldManipulator extends BlockModContainer {

	public BlockWorldManipulator(Material p_i45394_1_) {
		super(p_i45394_1_);
		setUnlocalizedName("worldManipulator");
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			TileEntityWorldManipulator wsm = (TileEntityWorldManipulator) worldIn.getTileEntity(pos);
			if(wsm != null) {
				if(wsm.getStackInSlot(0) != null && wsm.getStackInSlot(0).getItem() instanceof ItemWorldBinder) {
					if(playerIn.isSneaking()) {
						EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), wsm.removeStackFromSlot(0));
						worldIn.spawnEntityInWorld(item);
					} else {
							if(wsm.getStackInSlot(0) != null && wsm.getStackInSlot(0).getItem() instanceof ItemWorldBinder) {
								if(wsm.getStackInSlot(0).hasTagCompound()) {
									if(wsm.getStackInSlot(0).getTagCompound().getBoolean("IsSet")) {
										int[] temp = wsm.getStackInSlot(0).getTagCompound().getIntArray("WorldPos");
										BlockPos newPos = new BlockPos(temp[0], temp[1], temp[2]);
										if(worldIn.provider.getDimensionId() == wsm.getStackInSlot(0).getTagCompound().getInteger("DimID")) {
											IBlockState blockState = worldIn.getBlockState(newPos);
											if(blockState != null)
											blockState.getBlock().onBlockActivated(worldIn, newPos, blockState, playerIn, side, hitX, hitY, hitZ);
										} else {
											World world = DimensionManager.getWorld(wsm.getStackInSlot(0).getTagCompound().getInteger("DimID"));
											if(world != null && world.isRemote) {
												IBlockState blockState = world.getBlockState(newPos);
												if(blockState != null)
												blockState.getBlock().onBlockActivated(world, newPos, blockState, playerIn, side, hitX, hitY, hitZ);
											}
										}
									}
								}
							}
						}
					} else {
						if(playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() instanceof ItemWorldBinder) {
							wsm.setInventorySlotContents(0, playerIn.getHeldItem());
							playerIn.setCurrentItemOrArmor(0, null);
						}
					}
				}
			}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityWorldManipulator(); }
}
