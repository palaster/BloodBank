package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;

public class ItemLetter extends ItemModSpecial {

    public ItemLetter() {
        super();
        setUnlocalizedName("letter");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 1; }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote && playerIn.isSneaking()) {
            playerIn.openGui(BloodBank.instance, 3, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
