package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityEtherealChest;

public class KPEtherealChest implements IKnowledgePiece {

    @Override
    public String getName() { return "bb.kp.etherealChest"; }

    @Override
    public int getPrice() { return 0; }

    @Override
    public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn); }

    @Override
    public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        switch(facing) {
            case DOWN: {
                pos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
                break;
            }
            case UP: {
                pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
                break;
            }
            case NORTH: {
                pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
                break;
            }
            case SOUTH: {
                pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
                break;
            }
            case WEST: {
                pos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
                break;
            }
            case EAST: {
                pos = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
                break;
            }
        }
        if(worldIn.getBlockState(pos) == null || worldIn.getBlockState(pos).getBlock() == Blocks.AIR) {
            worldIn.setBlockState(pos, BBBlocks.etherealChest.getDefaultState());
            TileEntityEtherealChest ec = (TileEntityEtherealChest) worldIn.getTileEntity(pos);
            if(ec != null)
                if(BBApi.getEtherealChest(playerIn) != null)
                    ec.readFromNBT(BBApi.getEtherealChest(playerIn));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) { return false; }
}
