package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;

public class ItemUndeadMonitor extends ItemModSpecial {

    public ItemUndeadMonitor() {
        super();
        setUnlocalizedName("undeadMonitor");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 1; }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote) {
            playerIn.openGui(BloodBank.instance, 2, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    public void receiveButtonEvent(int buttonId, EntityPlayer player) {
        switch(buttonId) {}
    }
}
