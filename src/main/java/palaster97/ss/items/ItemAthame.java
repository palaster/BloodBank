package palaster97.ss.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import palaster97.ss.entities.extended.SSExtendedPlayer;

public class ItemAthame extends ItemModSpecial {

    public ItemAthame() {
        super();
        setUnlocalizedName("athame");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!playerIn.worldObj.isRemote)
            if(SSExtendedPlayer.get(playerIn) != null) {
                playerIn.attackEntityFrom(DamageSource.magic, .5f);
                SSExtendedPlayer.get(playerIn).addBlood(100);
            }
        return itemStackIn;
    }
}
