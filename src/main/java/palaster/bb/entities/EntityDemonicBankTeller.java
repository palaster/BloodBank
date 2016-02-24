package palaster.bb.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import palaster.bb.api.BBApi;
import palaster.bb.api.recipes.RecipeLetter;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.extended.BBExtendedPlayer;
import palaster.bb.inventories.InventoryModLetter;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemLetter;

public class EntityDemonicBankTeller extends EntityLiving {

    public EntityDemonicBankTeller(World world) {
        super(world);
        tasks.addTask(0, new EntityAISwimming(this));
        setSize(0.6F, 1.95F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        if(!worldObj.isRemote) {
            if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemLetter) {
                InventoryModLetter temp = new InventoryModLetter(player.getHeldItem());
                if(temp != null) {
                    int temp1 = 0;
                    for(int x = 0; x < temp.getSizeInventory(); x++)
                        if(temp.getStackInSlot(x) != null && temp.getStackInSlot(x).getItem() != Item.getItemFromBlock(Blocks.air))
                            temp1++;
                    ItemStack[] inputs = new ItemStack[temp1];
                    for(int i = 0; i < temp.getSizeInventory(); i++)
                        if(temp.getStackInSlot(i) != null)
                            inputs[i] = temp.getStackInSlot(i);
                    for(RecipeLetter lr : BBApi.letterRecipes) {
                        if(lr != null)
                            if(lr.matches(inputs))
                                player.setCurrentItemOrArmor(0, lr.getOutput());
                    }
                }
            } else {
                if(player.isSneaking()) {
                    setDead();
                    EntityItem bankID = new EntityItem(worldObj, player.posX, player.posY, player.posZ, new ItemStack(BBItems.bbResources, 1, 1));
                    worldObj.spawnEntityInWorld(bankID);
                    return true;
                } else if(BBExtendedPlayer.get(player) != null) {
                    if(BBExtendedPlayer.get(player).getBloodMax() <= 0)
                        BBPlayerHelper.sendChatMessageToPlayer(player, "You do not have an account with this bank.");
                    if(BBExtendedPlayer.get(player).getBloodMax() > 0)
                        BBPlayerHelper.sendChatMessageToPlayer(player, "You current balance is " + BBExtendedPlayer.get(player).getCurrentBlood() + " out of " + BBExtendedPlayer.get(player).getBloodMax());
                    return true;
                }
            }
        }
        return false;
    }
}
