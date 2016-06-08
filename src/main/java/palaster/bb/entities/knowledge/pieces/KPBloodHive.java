package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLiving;
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

public class KPBloodHive implements IKnowledgePiece {

	@Override
	public String getName() { return "bb.kp.bloodHive"; }

	@Override
	public int getPrice() { return 500; }

	@Override
	public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return null; }

	@Override
	public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return null; }

	@Override
	public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.worldObj.isRemote && target instanceof EntityLiving)
			if(target.getActivePotionEffect(BBPotions.bloodHive) == null) {
				target.addPotionEffect(new PotionEffect(BBPotions.bloodHive, 1200, 0, false, true));
				return true;
			}
		return false;
	}
}
