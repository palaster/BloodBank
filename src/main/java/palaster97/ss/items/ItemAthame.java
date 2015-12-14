package palaster97.ss.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import palaster97.ss.ScreamingSouls;
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
                playerIn.attackEntityFrom(DamageSource.magic, 1f);
                SSExtendedPlayer.get(playerIn).addBlood(100);
            }
        return itemStackIn;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(!player.worldObj.isRemote)
            if(entity instanceof EntityLivingBase)
                if(SSExtendedPlayer.get(player) != null) {
                    entity.attackEntityFrom(DamageSource.magic, 1f);
                    SSExtendedPlayer.get(player).addBlood(100);
                    return true;
                }
        return false;
    }

    @Override
    public Item setContainerItem(Item containerItem) { return this; }
}
