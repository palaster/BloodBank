package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IVampiric;

public class ItemStaffTime extends ItemModStaff implements IVampiric {

	public ItemStaffTime(ResourceLocation rl) {
		super(rl);
		powers = new String[] {"bb.staff.time.0"};
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			worldIn.provider.setWorldTime(worldIn.provider.getWorldTime() + 12000);
			itemStackIn.damageItem(256, playerIn);
			return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
