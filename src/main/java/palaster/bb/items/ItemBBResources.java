package palaster.bb.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.EntityDemonicBankTeller;
import palaster.bb.libs.LibMod;

import java.util.List;

public class ItemBBResources extends Item {

    public static String[] names = new String[]{"bankContract", "bankID", "magicDust"};

    public ItemBBResources() {
        super();
        setCreativeTab(CreativeTabBB.tabSS);
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(1);
        setUnlocalizedName("bbResources");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
            if(itemStackIn.getItemDamage() == 0) {
                if(BBApi.getMaxBlood(playerIn) <= 0) {
                    BBApi.setMaxBlood(playerIn, 2000);
                    BBPlayerHelper.sendChatMessageToPlayer(playerIn, "Welcome to the First National Bank of Blood, you start out with a max balance of 2000. Use this bank ID card to keep in contact.");
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(this, 1, 1));
                }
            }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(stack.getItemDamage() == 1) {
                if(BBApi.getMaxBlood(playerIn) > 0) {
                    EntityDemonicBankTeller dbt = new EntityDemonicBankTeller(worldIn);
                    dbt.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
                    worldIn.spawnEntityInWorld(dbt);
                    playerIn.setHeldItem(hand, null);
                    return EnumActionResult.SUCCESS;
                }
            }
        return EnumActionResult.PASS;
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
