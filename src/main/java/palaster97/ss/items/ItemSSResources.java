package palaster97.ss.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.EntityDemonicBankTeller;
import palaster97.ss.entities.extended.SSExtendedPlayer;
import palaster97.ss.libs.LibMod;

import java.util.List;

public class ItemSSResources extends Item {

    public static String[] names = new String[]{"bankContract", "bankID"};

    public ItemSSResources() {
        super();
        setCreativeTab(CreativeTabSS.tabSS);
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(1);
        setUnlocalizedName("ssResources");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!worldIn.isRemote)
            if(itemStackIn.getItemDamage() == 0)
                if(SSExtendedPlayer.get(playerIn) != null && SSExtendedPlayer.get(playerIn).getBloodMax() <= 0) {
                    SSExtendedPlayer.get(playerIn).setBloodMax(2000);
                    SSPlayerHelper.sendChatMessageToPlayer(playerIn, "Welcome to the First National Bank of Blood, you start out with a max balance of 2000. Use this bank ID card to keep in contact.");
                    EntityItem itemEntity = new EntityItem(worldIn, playerIn.posX, playerIn.posY + .5, playerIn.posZ, new ItemStack(this, 1, 1));
                    worldIn.spawnEntityInWorld(itemEntity);
                    return null;
                }
        return itemStackIn;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(stack.getItemDamage() == 1)
                if(SSExtendedPlayer.get(playerIn) != null && SSExtendedPlayer.get(playerIn).getBloodMax() > 0) {
                    EntityDemonicBankTeller dbt = new EntityDemonicBankTeller(worldIn);
                    dbt.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
                    worldIn.spawnEntityInWorld(dbt);
                    playerIn.setCurrentItemOrArmor(0, null);
                    return true;
                }
        return false;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName() + "." + names[stack.getItemDamage()]; }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        GameRegistry.registerItem(this, unlocalizedName);
        return super.setUnlocalizedName(unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void setItemRender(String name, int i) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}
