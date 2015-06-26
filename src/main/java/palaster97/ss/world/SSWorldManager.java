package palaster97.ss.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import palaster97.ss.blocks.tile.TileEntityVoidAnchor;

public class SSWorldManager implements IWorldAccess {

	@Override
	public void markBlockForUpdate(BlockPos pos) {}

	@Override
	public void notifyLightSet(BlockPos pos) {}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}

	@Override
	public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {}

	@Override
	public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) {}

	@Override
	public void spawnParticle(int p_180442_1_, boolean p_180442_2_, double p_180442_3_, double p_180442_5_, double p_180442_7_, double p_180442_9_, double p_180442_11_, double p_180442_13_, int... p_180442_15_) {}

	@Override
	public void onEntityAdded(Entity entityIn) {}

	@Override
	public void onEntityRemoved(Entity entityIn) {
		if(entityIn != null && entityIn instanceof EntityItem && entityIn.posY < 0) {
			EntityItem item = (EntityItem) entityIn;
			for(WorldServer ws : DimensionManager.getWorlds())
				for(int i = 0; i < ws.loadedTileEntityList.size(); i++)
					if(ws.loadedTileEntityList.get(i) != null && ws.loadedTileEntityList.get(i) instanceof TileEntity) {
						if(ws.loadedTileEntityList.get(i) instanceof TileEntityVoidAnchor) {
							TileEntityVoidAnchor va = (TileEntityVoidAnchor) ws.loadedTileEntityList.get(i);
							for(int j = 0; j < va.getSizeInventory(); j++)
								if(va.getStackInSlot(j) == null) {
									va.setInventorySlotContents(j, item.getEntityItem());
									return;
								} else if(va.getStackInSlot(j) != null && va.getStackInSlot(j).getItem() == item.getEntityItem().getItem())
									if(va.getStackInSlot(j).stackSize + item.getEntityItem().stackSize <= 64) {
										va.setInventorySlotContents(j, new ItemStack(va.getStackInSlot(j).getItem(), va.getStackInSlot(j).stackSize + item.getEntityItem().stackSize, va.getStackInSlot(j).getItemDamage()));
										return;
									}
						} else
							continue;
					}
		}
	}

	@Override
	public void playRecord(String recordName, BlockPos blockPosIn) {}

	@Override
	public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {}

	@Override
	public void playAusSFX(EntityPlayer p_180439_1_, int p_180439_2_, BlockPos blockPosIn, int p_180439_4_) {}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {}
}
