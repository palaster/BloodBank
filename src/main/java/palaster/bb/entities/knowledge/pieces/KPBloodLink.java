package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import palaster.bb.entities.knowledge.BBKnowledgePiece;
import palaster.bb.api.BBApi;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.extended.BBExtendedPlayer;

public class KPBloodLink extends BBKnowledgePiece {

    public KPBloodLink() { super("bb.kp.bloodLink", 2500); }

    @Override
    public void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        BBExtendedPlayer props = BBExtendedPlayer.get(playerIn);
        if(props.getLinked() == null && target instanceof EntityLiving && BBApi.checkExcludesForEntity((EntityLiving) target)) {
            props.consumeBlood(getPrice());
            props.linkEntity((EntityLiving) target);
        } else
            BBPlayerHelper.sendChatMessageToPlayer(playerIn, StatCollector.translateToLocal("bb.misc.alreadyLinked"));
    }

    @Override
    public void onBookRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {}

    @Override
    public void onBookUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {}
}
