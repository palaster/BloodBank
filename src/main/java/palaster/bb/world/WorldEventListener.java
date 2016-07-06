package palaster.bb.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import palaster.bb.blocks.tile.TileEntityVoidAnchor;

public class WorldEventListener implements IWorldEventListener {

	@Override
	public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {}

	@Override
	public void playSoundToAllNearExcept(EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {}

	@Override
	public void notifyLightSet(BlockPos pos) {}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}

	@Override
	public void spawnParticle(int p_180442_1_, boolean p_180442_2_, double p_180442_3_, double p_180442_5_, double p_180442_7_, double p_180442_9_, double p_180442_11_, double p_180442_13_, int... p_180442_15_) {}

	@Override
	public void onEntityAdded(Entity entityIn) {}

	@Override
	public void onEntityRemoved(Entity entityIn) {
		if(!entityIn.worldObj.isRemote)
			if(entityIn != null)
				if(entityIn instanceof EntityItem && entityIn.posY < 0) {
					EntityItem item = (EntityItem) entityIn;
					for(WorldServer ws : DimensionManager.getWorlds())
						for(int i = 0; i < ws.loadedTileEntityList.size(); i++)
							if(ws.loadedTileEntityList.get(i) != null && ws.loadedTileEntityList.get(i) instanceof TileEntity) {
								if(ws.loadedTileEntityList.get(i) instanceof TileEntityVoidAnchor) {
									TileEntityVoidAnchor va = (TileEntityVoidAnchor) ws.loadedTileEntityList.get(i);
									for(int j = 0; j < va.getSizeInventory(); j++)
										if(va.getItemHandler().getStackInSlot(j) == null) {
											va.getItemHandler().setStackInSlot(j, item.getEntityItem());
											return;
										} else if(va.getItemHandler().getStackInSlot(j) != null && va.getItemHandler().getStackInSlot(j).getItem() == item.getEntityItem().getItem())
											if(va.getItemHandler().getStackInSlot(j).stackSize + item.getEntityItem().stackSize <= 64) {
												va.getItemHandler().setStackInSlot(j, new ItemStack(va.getItemHandler().getStackInSlot(j).getItem(), va.getItemHandler().getStackInSlot(j).stackSize + item.getEntityItem().stackSize, va.getItemHandler().getStackInSlot(j).getItemDamage()));
												return;
											}
								} else
									continue;
							}
				}
	}

	@Override
	public void playRecord(SoundEvent soundIn, BlockPos pos) {}

	@Override
	public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {}

	@Override
	public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {}
}
