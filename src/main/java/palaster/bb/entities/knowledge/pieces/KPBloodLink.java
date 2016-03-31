package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.capabilities.entities.BloodBankCapability;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.knowledge.BBKnowledgePiece;

public class KPBloodLink extends BBKnowledgePiece {

    public KPBloodLink() { super("bb.kp.bloodLink", 2500); }

    @Override
    public void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        final BloodBankCapability.IBloodBank bloodBank = playerIn.getCapability(BBEventHandler.bloodBankCap, null);
        if(bloodBank.getLinked() == null && target instanceof EntityLiving && BBApi.checkExcludesForEntity((EntityLiving) target)) {
            bloodBank.consumeBlood(playerIn, getPrice());
            bloodBank.linkEntity((EntityLiving) target);
        } else
            BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.translateToLocal("bb.misc.alreadyLinked"));
    }

    @Override
    public void onBookRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {}

    @Override
    public void onBookUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {}
}
