package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.blocks.tile.TileEntityCommunityTool;
import palaster.bb.core.helpers.BBItemStackHelper;

public class BlockCommunityTool extends BlockModContainer {

    public BlockCommunityTool(Material material) {
        super(material);
        setBlockUnbreakable();
        setUnlocalizedName("communityTool");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
            TileEntityCommunityTool ct = (TileEntityCommunityTool) worldIn.getTileEntity(pos);
            if(ct != null) {
                if(ct.getOwner() == null)
                    ct.setOwner(playerIn.getUniqueID());
                else {
                    if(playerIn.isSneaking() && ct.getOwner().equals(playerIn.getUniqueID())) {
                        breakBlock(worldIn, pos, state);
                        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                        worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this)));
                        return true;
                    }
                    if(ct.getStackInSlot(0) != null) {
                        if(ct.getOwner().equals(playerIn.getUniqueID())) {
                            if(heldItem == null) {
                                playerIn.setHeldItem(EnumHand.MAIN_HAND, ct.getStackInSlot(0));
                                ct.setInventorySlotContents(0, null);
                                return true;
                            }
                        } else {
                            ItemStack ghostStack = ct.getStackInSlot(0).copy();
                            if(heldItem == null) {
                                playerIn.setHeldItem(hand, BBItemStackHelper.setCountDown(ghostStack, 6000));
                                return true;
                            }
                        }
                    } else {
                        if(ct.getOwner().equals(playerIn.getUniqueID()))
                            if(heldItem != null && heldItem.getMaxStackSize() == 1) {
                                ct.setInventorySlotContents(0, heldItem);
                                playerIn.setHeldItem(EnumHand.MAIN_HAND, null);
                                return true;
                            }
                    }
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityCommunityTool(); }
}
