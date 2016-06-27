package palaster.bb.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BBWorldHelper {

    public static BlockPos findBlockVicinityFromPlayer(Block block, World world, EntityPlayer player, int range, int rangeY) {
        if(!player.worldObj.isRemote)
            for(int i = -range; i < range + 1; i++)
                for(int j = -rangeY; j < rangeY + 1; j++)
                    for(int k = -range; k < range + 1; k++) {
                        int x = (int) player.posX + i;
                        int y = (int) player.posY + j;
                        int z = (int) player.posZ + k;
                        IBlockState blockState = world.getBlockState(new BlockPos(x, y, z));
                        if(blockState != null)
                            if(blockState.getBlock() == block)
                                return new BlockPos(x, y, z);
                    }
        return null;
    }
}