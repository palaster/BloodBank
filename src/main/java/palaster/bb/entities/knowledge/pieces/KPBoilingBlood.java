package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IKnowledgePiece;

public class KPBoilingBlood implements IKnowledgePiece {

    @Override
    public String getName() { return "bb.kp.boilingBlood"; }

    @Override
    public int getPrice() { return 500; }

    @Override
    public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn); }

    @Override
    public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return EnumActionResult.PASS; }

    @Override
    public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        target.setFire(10);
        return true;
    }
}
