package palaster.bb.entities.knowledge.pieces;

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
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.careers.CareerUnkindled;

public class KPUndeadSummon implements IKnowledgePiece {

	@Override
	public String getName() { return "bb.kp.undeadSummon"; }

	@Override
	public int getPrice() { return 500; }

	@Override
	public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn); }

	@Override
	public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			for(Entity entity : worldIn.getLoadedEntityList())
				if(entity instanceof EntityPlayer) {
					final IRPG rpg = RPGCapabilityProvider.get((EntityPlayer) entity);
					if(rpg != null)
						if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled) {
							entity.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
							return EnumActionResult.SUCCESS;
						}
				}
			BBPlayerHelper.sendChatMessageToPlayer(playerIn, net.minecraft.util.text.translation.I18n.translateToLocal("bb.undead.cantFind"));
		}
		return EnumActionResult.PASS;
	}

	@Override
	public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) { return false; }
}
