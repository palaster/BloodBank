package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster.bb.blocks.tile.TileEntityBloodHeater;
import palaster.bb.items.ItemPlayerBinder;

public class BlockBloodHeater extends BlockModContainer {

    public BlockBloodHeater(Material material) {
        super(material);
        setUnlocalizedName("bloodHeater");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() instanceof ItemPlayerBinder) {
                TileEntityBloodHeater bh = (TileEntityBloodHeater) worldIn.getTileEntity(pos);
                if(bh != null) {
                    bh.setInventorySlotContents(0, playerIn.getHeldItem());
                    playerIn.setCurrentItemOrArmor(0, null);
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

    // Facing Logic
}
