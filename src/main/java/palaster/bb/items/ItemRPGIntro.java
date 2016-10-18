package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityDefault;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.items.IRecieveButton;
import palaster.bb.core.proxy.CommonProxy;
import palaster.libpal.items.ItemModSpecial;

public class ItemRPGIntro extends ItemModSpecial implements IRecieveButton {

	public ItemRPGIntro(ResourceLocation rl) { super(rl); }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null) {
				CommonProxy.syncPlayerRPGCapabilitiesToClient(playerIn);
				playerIn.openGui(BloodBank.instance, 0, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
	
	public void receiveButtonEvent(int buttonId, EntityPlayer player) {
		final IRPG rpg = RPGCapabilityProvider.get(player);
		if(rpg != null) {
			switch(buttonId) {
				case 0: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getConstitution() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setConstitution(player, rpg.getConstitution() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 1: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getStrength() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setStrength(player, rpg.getStrength() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 2: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getDefense() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setDefense(rpg.getDefense() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 3: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getDexterity() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setDexterity(player, rpg.getDexterity() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 4: {
					if(player.experienceLevel >= RPGCapabilityDefault.getExperienceCostForNextLevel(player) && rpg.getIntelligence() < RPGCapabilityDefault.MAX_LEVEL) {
						if(player.experienceLevel - RPGCapabilityDefault.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(RPGCapabilityDefault.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(RPGCapabilityDefault.getExperienceCostForNextLevel(player));
						rpg.setIntelligence(rpg.getIntelligence() + 1);
						CommonProxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
			}
		}
	}
}
