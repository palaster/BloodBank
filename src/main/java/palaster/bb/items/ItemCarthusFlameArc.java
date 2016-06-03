package palaster.bb.items;

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
import palaster.bb.api.capabilities.items.IFlameSpell;
import palaster.bb.core.helpers.BBPlayerHelper;

public class ItemCarthusFlameArc extends ItemModSpecial implements IFlameSpell {

    public ItemCarthusFlameArc() {
        super();
        setUnlocalizedName("carthusFlameArc");
    }

    @Override
    public int getSpellCost() { return 30; }

    @Override
    public boolean onSpellLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        BBPlayerHelper.sendChatMessageToPlayer(player, "Carthus Flame Arc Spell Left Click Entity");
        return true;
    }

    @Override
    public EnumActionResult onSpellUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Carthus Flame Arc Spell Use");
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onSpellRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Carthus Flame Arc Spell Right Click");
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public boolean spellInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Carthus Flame Arc Spell Interaction For Entity");
        return true;
    }
}
