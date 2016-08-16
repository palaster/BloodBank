package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityDefault;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.items.IRecieveButton;

public class ItemRPGIntro extends ItemModSpecial implements IRecieveButton {

	public ItemRPGIntro() {
		super();
		setUnlocalizedName("rpgIntro");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null) {
				BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(playerIn);
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
					if(player.experienceLevel > BBApi.getExperienceCostForNextLevel(player) && rpg.getConstitution() < RPGCapabilityDefault.maxLevel) {
						if(player.experienceLevel - BBApi.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(BBApi.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(BBApi.getExperienceCostForNextLevel(player));
						rpg.setConstitution(rpg.getConstitution() + 1);
						BBApi.calculateConstitutionBoost(player);
						BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 1: {
					if(player.experienceLevel > BBApi.getExperienceCostForNextLevel(player) && rpg.getStrength() < RPGCapabilityDefault.maxLevel) {
						if(player.experienceLevel - BBApi.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(BBApi.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(BBApi.getExperienceCostForNextLevel(player));
						rpg.setStrength(rpg.getStrength() + 1);
						BBApi.calculateStrengthBoost(player);
						BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 2: {
					if(player.experienceLevel > BBApi.getExperienceCostForNextLevel(player) && rpg.getDefense() < RPGCapabilityDefault.maxLevel) {
						if(player.experienceLevel - BBApi.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(BBApi.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(BBApi.getExperienceCostForNextLevel(player));
						rpg.setDefense(rpg.getDefense() + 1);
						BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
				case 3: {
					if(player.experienceLevel > BBApi.getExperienceCostForNextLevel(player) && rpg.getDexterity() < RPGCapabilityDefault.maxLevel) {
						if(player.experienceLevel - BBApi.getExperienceCostForNextLevel(player) <= 0)
							player.removeExperienceLevel(player.experienceLevel);
						else if(BBApi.getExperienceCostForNextLevel(player) > 0)
							player.removeExperienceLevel(BBApi.getExperienceCostForNextLevel(player));
						rpg.setDexterity(rpg.getDexterity() + 1);
						BBApi.calculateDexterityBoost(player);
						BloodBank.proxy.syncPlayerRPGCapabilitiesToClient(player);
					}
					break;
				}
			}
		}
	}
}
