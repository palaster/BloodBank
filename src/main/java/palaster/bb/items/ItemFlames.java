package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.items.IFlameSpell;
import palaster.bb.core.helpers.BBItemStackHelper;

public class ItemFlames extends ItemModSpecial {

    public ItemFlames() {
        super();
        setUnlocalizedName("flames");
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(!player.worldObj.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack) != null && BBItemStackHelper.getItemStackFromItemStack(stack).getItem() instanceof IFlameSpell)
                if(BBApi.getFocus(player) >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).getSpellCost())
                    if(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).onSpellLeftClickEntity(stack, player, entity))
                        BBApi.useFocus(player, ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).getSpellCost());
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack) != null && BBItemStackHelper.getItemStackFromItemStack(stack).getItem() instanceof IFlameSpell)
                if(BBApi.getFocus(playerIn) >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).getSpellCost()) {
                    System.out.println("Success 1");
                    if(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).onSpellUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ) != null) {
                        System.out.println("Success 2");
                        BBApi.useFocus(playerIn, ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).getSpellCost());
                    }
                }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(itemStackIn) != null && BBItemStackHelper.getItemStackFromItemStack(itemStackIn).getItem() instanceof IFlameSpell)
                if(BBApi.getFocus(playerIn) >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn).getItem()).getSpellCost())
                    if(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn).getItem()).onSpellRightClick(itemStackIn, worldIn, playerIn, hand) != null)
                        BBApi.useFocus(playerIn, ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn).getItem()).getSpellCost());
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.worldObj.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack) != null && BBItemStackHelper.getItemStackFromItemStack(stack).getItem() instanceof IFlameSpell)
                if(BBApi.getFocus(playerIn) >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).getSpellCost())
                    if(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).spellInteractionForEntity(stack, playerIn, target, hand))
                        BBApi.useFocus(playerIn, ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack).getItem()).getSpellCost());
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
