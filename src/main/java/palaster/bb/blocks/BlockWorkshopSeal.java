package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.entities.careers.CareerWorkshopWitch;
import palaster.bb.world.BBWorldSaveData;
import palaster.bb.world.chunk.WorkshopChunkWrapper;
import palaster.libpal.blocks.BlockMod;

public class BlockWorkshopSeal extends BlockMod {

	public BlockWorkshopSeal(ResourceLocation rl, Material material) {
		super(rl, material);
		setBlockUnbreakable();
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if(placer instanceof EntityPlayer)
			if(!worldIn.isRemote) {
				BBWorldSaveData wsd = BBWorldSaveData.get(worldIn);
				IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) placer);
				if(wsd != null && rpg != null && rpg.getCareer() != null && rpg.getCareer() instanceof CareerWorkshopWitch)
					if(!((CareerWorkshopWitch) rpg.getCareer()).isLeader() && ((CareerWorkshopWitch) rpg.getCareer()).getLeader() == null) {
						Chunk startChunk = worldIn.getChunkFromBlockCoords(pos);
						if(startChunk != null) {
							for(int x = startChunk.xPosition - 1; x < startChunk.xPosition + 1; x++)
								for(int z = startChunk.zPosition - 1; z < startChunk.zPosition + 1; z++)
									if(worldIn.getChunkFromChunkCoords(x, z) != null) {
										WorkshopChunkWrapper wcw = wsd.getWorkshopChunkWrapper(x, z);
										if(wcw != null)
											if(wcw.getOwner() != null)
												return null;
									}
							((CareerWorkshopWitch) rpg.getCareer()).setIsLeader(true);
							for(int x = startChunk.xPosition - 1; x < startChunk.xPosition + 1; x++)
								for(int z = startChunk.zPosition - 1; z < startChunk.zPosition + 1; z++)
									wsd.addWorkshopChunkWrapper(new WorkshopChunkWrapper(worldIn.provider.getDimension(), placer.getUniqueID(), worldIn.getChunkFromChunkCoords(x, z)));
							super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
						}
					}
			}
		return Blocks.AIR.getDefaultState();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			BBWorldSaveData wsd = BBWorldSaveData.get(worldIn);
			Chunk startChunk = worldIn.getChunkFromBlockCoords(pos);
			if(wsd != null && startChunk != null)
				for(int x = startChunk.xPosition - 1; x < startChunk.xPosition + 1; x++)
					for(int z = startChunk.zPosition - 1; z < startChunk.zPosition + 1; z++)
						if(worldIn.getChunkFromChunkCoords(x, z) != null) {
							WorkshopChunkWrapper wcw = wsd.getWorkshopChunkWrapper(x, z);
							if(wcw != null)
								if(wcw.getOwner() != null)
									wsd.removeWorkshopChunkWrapper(wcw);
						}
		}
		super.breakBlock(worldIn, pos, state);
	}
}