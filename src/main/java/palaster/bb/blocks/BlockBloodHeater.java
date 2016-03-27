package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.blocks.tile.TileEntityBloodHeater;
import palaster.bb.items.ItemPlayerBinder;

public class BlockBloodHeater extends BlockModContainer {

    public BlockBloodHeater(Material material) {
        super(material);
        setUnlocalizedName("bloodHeater");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.getHeldItemMainhand() != null && playerIn.getHeldItemMainhand().getItem() instanceof ItemPlayerBinder) {
                TileEntityBloodHeater bh = (TileEntityBloodHeater) worldIn.getTileEntity(pos);
                if(bh != null) {
                    bh.setInventorySlotContents(0, playerIn.getHeldItemMainhand());
                    playerIn.setHeldItem(EnumHand.MAIN_HAND, null);
                }
            } else {
                TileEntityBloodHeater bh = (TileEntityBloodHeater) worldIn.getTileEntity(pos);
                if(bh != null && bh.getStackInSlot(0) != null) {
                    EntityItem pb = new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, bh.getStackInSlot(0));
                    bh.setInventorySlotContents(0, null);
                    worldIn.spawnEntityInWorld(pb);
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityBloodHeater(); }

    // Make Facing Logic with BlockState
}
