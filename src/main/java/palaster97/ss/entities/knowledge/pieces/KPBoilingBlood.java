package palaster97.ss.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.extended.SSExtendedPlayer;
import palaster97.ss.entities.knowledge.SSKnowledgePiece;

public class KPBoilingBlood extends SSKnowledgePiece {

    public KPBoilingBlood() {
        super("ss.kp.boilingBlood", 500);
    }

    @Override
    public void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        if(SSExtendedPlayer.get(playerIn) != null) {
            SSExtendedPlayer.get(playerIn).consumeBlood(getPrice());
            target.setFire(10);
        }
    }

    @Override
    public void onBookRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {}

    @Override
    public void onBookUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {}
}
