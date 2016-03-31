package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.capabilities.entities.BloodBankCapability;
import palaster.bb.core.handlers.BBEventHandler;

public class ItemAthame extends ItemModSpecial {

    public ItemAthame() {
        super();
        setUnlocalizedName("athame");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!playerIn.worldObj.isRemote) {
            final BloodBankCapability.IBloodBank bloodBank = playerIn.getCapability(BBEventHandler.bloodBankCap, null);
            if(bloodBank != null) {
                playerIn.attackEntityFrom(BloodBank.proxy.bbBlood, 1f);
                bloodBank.addBlood(100);
                return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(!player.worldObj.isRemote)
            if(entity instanceof EntityLivingBase) {
                final BloodBankCapability.IBloodBank bloodBank = player.getCapability(BBEventHandler.bloodBankCap, null);
                if(bloodBank != null) {
                    entity.attackEntityFrom(BloodBank.proxy.bbBlood, 1f);
                    bloodBank.addBlood(100);
                    return true;
                }
            }
        return false;
    }

    @Override
    public Item setContainerItem(Item containerItem) { return this; }
}
