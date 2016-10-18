package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.ISpecialArmorAbility;
import palaster.libpal.items.ItemModSpecial;

public class ItemArmorActivator extends ItemModSpecial {

	public ItemArmorActivator(ResourceLocation rl) { super(rl); }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote)
			if(!playerIn.isSneaking())
				if(playerIn.inventory.armorInventory[0] != null && playerIn.inventory.armorInventory[0].getItem() instanceof ISpecialArmorAbility) {
					((ISpecialArmorAbility) playerIn.inventory.armorInventory[0].getItem()).doArmorAbility(worldIn, playerIn);
					return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
				}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
