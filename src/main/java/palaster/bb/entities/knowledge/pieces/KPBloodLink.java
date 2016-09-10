package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.careers.CareerBloodSorcerer;

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
    	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
		if(rpg != null) {
			if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
				if(!((CareerBloodSorcerer) rpg.getCareer()).isLinked() && target instanceof EntityLiving && !BBApi.checkExcludesForEntity((EntityLiving) target)) {
					((CareerBloodSorcerer) rpg.getCareer()).linkEntity((EntityLiving) target);
		            return true;
		        } else
		        	BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.translateToLocal("bb.misc.alreadyLinked"));
			}
		}
        return false;
    }
}
