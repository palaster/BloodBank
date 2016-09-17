package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.entities.EntityDemonicBankTeller;

public class KPSummonDemonicBankTeller implements IKnowledgePiece {

	@Override
	public String getName() { return "bb.kp.summonDemonicBankTeller"; }

	@Override
	public int getPrice() { return 0; }

	@Override
	public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return ActionResult.newResult(EnumActionResult.PASS, itemStackIn); }

	@Override
	public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			EntityDemonicBankTeller dbt = new EntityDemonicBankTeller(worldIn);
			dbt.setPosition(pos.getX(), pos.getY() + .25, pos.getZ());
			worldIn.spawnEntityInWorld(dbt);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) { return false; }
}
