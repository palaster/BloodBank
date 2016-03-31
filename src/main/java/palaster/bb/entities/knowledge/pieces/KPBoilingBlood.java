package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.capabilities.entities.IBloodBank;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.entities.knowledge.BBKnowledgePiece;

public class KPBoilingBlood extends BBKnowledgePiece {

    public KPBoilingBlood() {
        super("bb.kp.boilingBlood", 500);
    }

    @Override
    public void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        final IBloodBank bloodBank = playerIn.getCapability(BloodBankCapabilityProvider.bloodBankCap, null);
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
