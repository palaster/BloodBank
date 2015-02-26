package palaster97.ss.inventories.slots;

import palaster97.ss.recipes.SoulCompressorCraftingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;

public class SlotSCCrafting extends Slot {

	private final InventoryCrafting craftMatrix;
    private final EntityPlayer thePlayer;
    private int amountCrafted;
    
	public SlotSCCrafting(EntityPlayer p_i45790_1_, InventoryCrafting p_i45790_2_, IInventory p_i45790_3_, int p_i45790_4_, int p_i45790_5_, int p_i45790_6_) {
		super(p_i45790_3_, p_i45790_4_, p_i45790_5_, p_i45790_6_);
		this.thePlayer = p_i45790_1_;
        this.craftMatrix = p_i45790_2_;
	}

	@Override
	public boolean isItemValid(ItemStack stack) { return false; }

	@Override
    public ItemStack decrStackSize(int p_75209_1_) {
        if(getHasStack()) amountCrafted += Math.min(p_75209_1_, getStack().stackSize);
        return super.decrStackSize(p_75209_1_);
    }

	@Override
    protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_) {
        amountCrafted += p_75210_2_;
        onCrafting(p_75210_1_);
    }

	@Override
    protected void onCrafting(ItemStack p_75208_1_) {
        if(amountCrafted > 0)
            p_75208_1_.onCrafting(thePlayer.worldObj, thePlayer, amountCrafted);
        amountCrafted = 0;
        if(p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.crafting_table))
            thePlayer.triggerAchievement(AchievementList.buildWorkBench);
        if(p_75208_1_.getItem() instanceof ItemPickaxe)
            thePlayer.triggerAchievement(AchievementList.buildPickaxe);
        if(p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.furnace))
            thePlayer.triggerAchievement(AchievementList.buildFurnace);
        if(p_75208_1_.getItem() instanceof ItemHoe)
            thePlayer.triggerAchievement(AchievementList.buildHoe);
        if(p_75208_1_.getItem() == Items.bread)
            thePlayer.triggerAchievement(AchievementList.makeBread);
        if(p_75208_1_.getItem() == Items.cake)
            thePlayer.triggerAchievement(AchievementList.bakeCake);
        if(p_75208_1_.getItem() instanceof ItemPickaxe && ((ItemPickaxe)p_75208_1_.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD)
            thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
        if(p_75208_1_.getItem() instanceof ItemSword)
            thePlayer.triggerAchievement(AchievementList.buildSword);
        if(p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.enchanting_table))
            thePlayer.triggerAchievement(AchievementList.enchantments);
        if (p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.bookshelf))
            thePlayer.triggerAchievement(AchievementList.bookcase);
        if(p_75208_1_.getItem() == Items.golden_apple && p_75208_1_.getMetadata() == 1)
            thePlayer.triggerAchievement(AchievementList.overpowered);
    }

	@Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, craftMatrix);
        onCrafting(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(playerIn);
        ItemStack[] aitemstack;
        if(CraftingManager.getInstance().findMatchingRecipe(craftMatrix, playerIn.worldObj) != null) {
        	aitemstack = CraftingManager.getInstance().func_180303_b(craftMatrix, playerIn.worldObj);
        } else 
        	aitemstack = SoulCompressorCraftingManager.getInstance().func_180303_b(craftMatrix, playerIn.worldObj);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
        
        for(int i = 0; i < aitemstack.length; ++i) {
            ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
            ItemStack itemstack2 = aitemstack[i];
            if(itemstack1 != null)
                craftMatrix.decrStackSize(i, 1);
            if(itemstack2 != null) {
                if(craftMatrix.getStackInSlot(i) == null)
                    craftMatrix.setInventorySlotContents(i, itemstack2);
                else if(!thePlayer.inventory.addItemStackToInventory(itemstack2))
                    thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
            }
        }
    }
}
