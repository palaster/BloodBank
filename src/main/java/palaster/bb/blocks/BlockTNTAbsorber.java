package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.blocks.tile.TileEntityTNTAbsorber;

public class BlockTNTAbsorber extends BlockModContainer {

	public BlockTNTAbsorber(Material material) {
		super(material);
		setUnlocalizedName("tntAbsorber");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e) {
		if(!e.getWorld().isRemote) {
			for(TileEntity te : e.getWorld().loadedTileEntityList)
				if(te != null && te instanceof TileEntityTNTAbsorber) {
					Chunk chunkTE = e.getWorld().getChunkFromBlockCoords(te.getPos());
					if(chunkTE != null) {
						Chunk chunkEntity = e.getWorld().getChunkFromBlockCoords(e.getEntity().getPosition());
						if(chunkEntity != null && chunkTE.equals(chunkEntity))
							if(e.getEntity() instanceof EntityTNTPrimed) {
								//e.getWorld().setBlockState(e.getEntity().getPosition(), Blocks.CAKE.getDefaultState());
								//e.getWorld().spawnEntityInWorld(new EntityItem(e.getWorld(), e.getEntity().getPosition().getX(), e.getEntity().getPosition().getY(), e.getEntity().getPosition().getZ(), new ItemStack(Item.getItemFromBlock(Blocks.CAKE), 1)));
								e.setCanceled(true);
								break;
							}
					}
				}
		}
	}
	
	@SubscribeEvent
	public void onExplosion(ExplosionEvent e) {
		if(!e.getWorld().isRemote) {
			for(TileEntity te : e.getWorld().loadedTileEntityList)
				if(te != null && te instanceof TileEntityTNTAbsorber) {
					Chunk chunkTE = e.getWorld().getChunkFromBlockCoords(te.getPos());
					if(chunkTE != null) {
						Chunk chunkEntity = e.getWorld().getChunkFromBlockCoords(new BlockPos(e.getExplosion().getPosition().xCoord, e.getExplosion().getPosition().yCoord, e.getExplosion().getPosition().zCoord));
						if(chunkEntity != null && chunkTE.equals(chunkEntity)) {
							e.setCanceled(true);
							//System.out.println("Play laugh.");
							break;
						}
					}
				}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityTNTAbsorber(); }
}
