package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.capabilities.entities.BloodBankCapability;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.entities.knowledge.BBKnowledgePiece;

public class KPBoilingBlood extends BBKnowledgePiece {

    public KPBoilingBlood() {
        super("bb.kp.boilingBlood", 500);
    }

    @Override
    public void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        final BloodBankCapability.IBloodBank bloodBank = playerIn.getCapability(BBEventHandler.bloodBankCap, null);
        if(bloodBank != null) {
            bloodBank.consumeBlood(playerIn, getPrice());
            target.setFire(10);
        }
    }

    @Override
    public void onBookRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {}

    @Override
    public void onBookUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {}
}
