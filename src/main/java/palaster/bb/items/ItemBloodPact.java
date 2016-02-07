package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster.bb.entities.extended.BBExtendedPlayer;

public class ItemBloodPact extends ItemModSpecial {

    public ItemBloodPact() {
        super();
        setUnlocalizedName("bloodPact");
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if(!worldIn.isRemote)
            if(BBExtendedPlayer.get(playerIn) != null)
                BBExtendedPlayer.get(playerIn).consumeBlood(50);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            /*
            EntityBloodPriest bp = new EntityBloodPriest(worldIn);
            bp.setOwner();
            bp.setTamed(true);
            worldIn.spawnEntityInWorld(bp);
            */
        }
        return false;
    }
}
