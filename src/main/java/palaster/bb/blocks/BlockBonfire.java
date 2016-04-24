package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.world.BBWorldSaveData;

public class BlockBonfire extends BlockMod {

    public BlockBonfire(Material material) {
        super(material);
        setUnlocalizedName("bonfire");
        setHarvestLevel("axe", 0);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if(!worldIn.isRemote)
            if(placer instanceof EntityPlayer)
                if(BBApi.isUndead((EntityPlayer) placer))
                    return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return Blocks.air.getDefaultState();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if(!worldIn.isRemote)
            if(placer instanceof EntityPlayer)
                if(BBApi.isUndead((EntityPlayer) placer)) {
                    BBWorldSaveData bbWorldSaveData = BBWorldSaveData.get(worldIn);
                    if(bbWorldSaveData != null)
                        bbWorldSaveData.addBonfire(pos);
                }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(!worldIn.isRemote) {
            BBWorldSaveData bbWorldSaveData = BBWorldSaveData.get(worldIn);
            if(bbWorldSaveData != null)
                bbWorldSaveData.removeBonfire(pos);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
