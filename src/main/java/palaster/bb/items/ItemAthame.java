package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster.bb.entities.extended.BBExtendedPlayer;

public class ItemAthame extends ItemModSpecial {

    public ItemAthame() {
        super();
        setUnlocalizedName("athame");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!playerIn.worldObj.isRemote)
            if(BBExtendedPlayer.get(playerIn) != null) {
                playerIn.attackEntityFrom(BBExtendedPlayer.bbBlood, 1f);
                BBExtendedPlayer.get(playerIn).addBlood(100);
            }
        return itemStackIn;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(!player.worldObj.isRemote)
            if(entity instanceof EntityLivingBase)
                if(BBExtendedPlayer.get(player) != null) {
                    entity.attackEntityFrom(BBExtendedPlayer.bbBlood, 1f);
                    BBExtendedPlayer.get(player).addBlood(100);
                    return true;
                }
        return false;
    }

    @Override
    public Item setContainerItem(Item containerItem) { return this; }
}
