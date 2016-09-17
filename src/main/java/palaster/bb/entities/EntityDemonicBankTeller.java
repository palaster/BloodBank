package palaster.bb.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.items.BBItems;
import palaster.bb.libs.LibResource;

public class EntityDemonicBankTeller extends EntityLiving {

    public EntityDemonicBankTeller(World world) {
        super(world);
        tasks.addTask(0, new EntityAISwimming(this));
        setSize(0.6F, 1.95F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand) {
        if(!worldObj.isRemote && hand == EnumHand.MAIN_HAND) {
            if(stack != null) {
            	final IRPG rpg = RPGCapabilityProvider.get(player);
				if(rpg != null)
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
						if(stack.getItem() instanceof ItemGlassBottle) {
							if(((CareerBloodSorcerer) rpg.getCareer()).getCurrentBlood() >= 2000)
								if(((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(2000) <= 0) {
		                        	player.setHeldItem(hand, new ItemStack(BBItems.bloodBottle));
		                        	return EnumActionResult.SUCCESS;
								}
						} else if(stack.getItem() == Items.APPLE) {
							if(((CareerBloodSorcerer) rpg.getCareer()).getMaxBlood() <= 2000) {
								if(stack.stackSize > 1) {
			                		stack.stackSize--;
			                		((CareerBloodSorcerer) rpg.getCareer()).setMaxBlood(10000);
			                	} else
			                		player.setHeldItem(hand, null);
							}
							return EnumActionResult.SUCCESS;
						} else if(ItemStack.areItemsEqual(stack, new ItemStack(Items.GOLDEN_APPLE, 1, 0))) {
							if(((CareerBloodSorcerer) rpg.getCareer()).getMaxBlood() <= 10000) {
								if(stack.stackSize > 1) {
			                		stack.stackSize--;
			                		((CareerBloodSorcerer) rpg.getCareer()).setMaxBlood(50000);
			                	} else
			                		player.setHeldItem(hand, null);
							}
							return EnumActionResult.SUCCESS;
						} else if(ItemStack.areItemsEqual(stack, new ItemStack(Items.GOLDEN_APPLE, 1, 1))) {
							if(((CareerBloodSorcerer) rpg.getCareer()).getMaxBlood() <= 50000) {
								if(stack.stackSize > 1) {
			                		stack.stackSize--;
			                		((CareerBloodSorcerer) rpg.getCareer()).setMaxBlood(100000);
			                	} else
			                		player.setHeldItem(hand, null);
							}
							return EnumActionResult.SUCCESS;
						}
					}
            } else {
                if(player.isSneaking()) {
                    setDead();
                    EntityItem bankID = new EntityItem(worldObj, player.posX, player.posY, player.posZ, new ItemStack(BBItems.bbResources, 1, 1));
                    worldObj.spawnEntityInWorld(bankID);
                    return EnumActionResult.SUCCESS;
                } else {
                	final IRPG rpg = RPGCapabilityProvider.get(player);
					if(rpg != null) {
						if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
							BBPlayerHelper.sendChatMessageToPlayer(player, "You current balance is " + ((CareerBloodSorcerer) rpg.getCareer()).getCurrentBlood() + " out of " + ((CareerBloodSorcerer) rpg.getCareer()).getMaxBlood());
						} else
							BBPlayerHelper.sendChatMessageToPlayer(player, "You do not have an account with this bank.");
	                    
					}
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return super.applyPlayerInteraction(player, vec, stack, hand);
    }
    
    @Override
    protected ResourceLocation getLootTable() { return LibResource.DEMONIC_BANK_TELLER_LOOT; }
}
