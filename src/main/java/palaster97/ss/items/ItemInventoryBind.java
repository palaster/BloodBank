package palaster97.ss.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.helpers.SSPlayerHelper;

import java.util.List;

public class ItemInventoryBind extends ItemModSpecial {

    public ItemInventoryBind() {
        super();
        setUnlocalizedName("inventoryBind");
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        if(stack.hasTagCompound()) {
            if(SSPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")) != null)
                tooltip.add(SSPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID")).getGameProfile().getName());
            else
                tooltip.add(StatCollector.translateToLocal("ss.misc.brokenPlayer"));
        } else
            tooltip.add(StatCollector.translateToLocal("ss.misc.broken"));
    }
}
