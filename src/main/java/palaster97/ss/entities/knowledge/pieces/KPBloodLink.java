package palaster97.ss.entities.knowledge.pieces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import palaster97.ss.api.SSApi;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.extended.SSExtendedPlayer;
import palaster97.ss.entities.knowledge.SSKnowledgePiece;

public class KPBloodLink extends SSKnowledgePiece {

    public KPBloodLink() { super("ss.kp.bloodLink", 2500); }

    @Override
    public void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        SSExtendedPlayer props = SSExtendedPlayer.get(playerIn);
        if(props.getLinked() == null && target instanceof EntityLiving && SSApi.checkExcludesForEntity((EntityLiving) target)) {
            props.consumeBlood(getPrice());
            props.linkEntity((EntityLiving) target);
        } else
            SSPlayerHelper.sendChatMessageToPlayer(playerIn, StatCollector.translateToLocal("ss.misc.alreadyLinked"));
    }

    @Override
    public void onBookRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {}

    @Override
    public void onBookUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {}
}
