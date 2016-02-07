package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster.bb.entities.extended.BBExtendedPlayer;
import palaster.bb.entities.knowledge.BBKnowledgePiece;

public class KPBoilingBlood extends BBKnowledgePiece {

    public KPBoilingBlood() {
        super("bb.kp.boilingBlood", 500);
    }

    @Override
    public void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        if(BBExtendedPlayer.get(playerIn) != null) {
            BBExtendedPlayer.get(playerIn).consumeBlood(getPrice());
            target.setFire(10);
        }
    }

    @Override
    public void onBookRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {}

    @Override
    public void onBookUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {}
}
