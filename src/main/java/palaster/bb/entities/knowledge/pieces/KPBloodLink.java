package palaster.bb.entities.knowledge.pieces;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.BloodBankCapability.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.core.helpers.BBPlayerHelper;

public class KPBloodLink implements IKnowledgePiece {

    @Override
    public String getName() { return "bb.kp.bloodLink"; }

    @Override
    public int getPrice() { return 2500; }

    @Override
    public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn); }

    @Override
    public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return EnumActionResult.PASS; }

    @Override
    public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
    	final IBloodBank bloodBank = BloodBankCapabilityProvider.get(playerIn);
		if(bloodBank != null) {
			if(!bloodBank.isLinked() && target instanceof EntityLiving && !BBApi.checkExcludesForEntity((EntityLiving) target)) {
				bloodBank.linkEntity((EntityLiving) target);
	            return true;
	        } else
	            BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.misc.alreadyLinked"));
		}
        return false;
    }
}
