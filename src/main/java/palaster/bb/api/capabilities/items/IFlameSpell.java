package palaster.bb.api.capabilities.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFlameSpell {

    int getSpellCost();

    boolean onSpellLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity);

    EnumActionResult onSpellUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);

    ActionResult<ItemStack> onSpellRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand);

    boolean spellInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand);
}
