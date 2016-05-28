package palaster.bb.api.capabilities.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IKnowledgePiece {

    String getName();

    int getPrice();

    ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand);

    EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);

    boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand);
}
