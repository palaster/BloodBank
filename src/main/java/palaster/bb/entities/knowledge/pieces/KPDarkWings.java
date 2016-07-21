package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.entities.effects.BBPotions;

public class KPDarkWings implements IKnowledgePiece {

	@Override
	public String getName() { return "bb.kp.darkWings"; }

	@Override
	public int getPrice() { return 2500; }

	@Override
	public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote)
			if(playerIn.getActivePotionEffect(BBPotions.darkWings) == null) {
				playerIn.addPotionEffect(new PotionEffect(BBPotions.darkWings, 12000, 0, false, true));
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return EnumActionResult.PASS; }

	@Override
	public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) { return false; }
}
