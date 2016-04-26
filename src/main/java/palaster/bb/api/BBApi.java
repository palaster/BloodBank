package palaster.bb.api;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapabilityProvider;
import palaster.bb.api.recipes.RecipeLetter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BBApi {

    private static List<Class<? extends EntityLiving>> excludeFromBloodLink = new ArrayList<Class<? extends EntityLiving>>();

    public static List<RecipeLetter> letterRecipes = new ArrayList<RecipeLetter>();

    public static void addEntityLivingToExclude(EntityLiving el) {
        if(el != null) {
            for(Class<? extends EntityLiving> classEL : excludeFromBloodLink)
                if(classEL.getName().equals(el.getClass().getName())) {
                    System.out.println(el.getClass().getName() + " has already been excluded.");
                    return;
                }
            excludeFromBloodLink.add(el.getClass());
        }
    }

    public static void addEntityLivingClassToExclude(Class<? extends EntityLiving> classELTemp) {
        if(classELTemp != null) {
            for(Class<? extends EntityLiving> classEL : excludeFromBloodLink)
                if(classEL.getName().equals(classELTemp.getName())) {
                    System.out.println(classELTemp.getName() + " has already been excluded.");
                    return;
                }
            excludeFromBloodLink.add(classELTemp);
        }
    }

    public static boolean checkExcludesForEntity(EntityLiving el) {
        for(Class<? extends EntityLiving> classEL : excludeFromBloodLink)
            if(classEL.getName().equals(el.getClass().getName()))
                return true;
        return false;
    }

    public static boolean checkExcludesForClass(Class<? extends  EntityLiving> classELTemp) {
        for(Class<? extends EntityLiving> classEL : excludeFromBloodLink)
            if(classEL.getName().equals(classELTemp.getName()))
                return true;
        return false;
    }

    public static RecipeLetter registerLetterRecipe(ItemStack output, Object... input) {
        if(input.length <= 9) {
            RecipeLetter recipe = new RecipeLetter(output, input);
            letterRecipes.add(recipe);
            return recipe;
        } else {
            System.out.println(output.getDisplayName() + " has a recipe larger than 9 items.");
            return null;
        }
    }

    // Blood Bank Methods

    public static void consumeBlood(EntityPlayer player, int amt) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            bloodBank.consumeBlood(player, amt);
    }

    public static int getCurrentBlood(EntityPlayer player) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            return bloodBank.getCurrentBlood();
        return 0;
    }

    public static void addBlood(EntityPlayer player, int amt) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            bloodBank.addBlood(amt);
    }

    public static void setCurrentBlood(EntityPlayer player, int amt) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            bloodBank.setCurrentBlood(amt);
    }

    public static int getMaxBlood(EntityPlayer player) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            return bloodBank.getBloodMax();
        return 0;
    }

    public static void setMaxBlood(EntityPlayer player, int amt) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            bloodBank.setBloodMax(amt);
    }

    public static void linkEntity(EntityPlayer player, EntityLiving entityLiving) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            bloodBank.linkEntity(entityLiving);
    }

    public static EntityLiving getLinked(EntityPlayer player) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            return bloodBank.getLinked();
        return null;
    }

    // Undead Methods

    public static boolean isUndead(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.isUndead();
        return false;
    }

    public static void setUndead(EntityPlayer player, boolean bool) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setUndead(bool);
    }

    public static void addSoul(EntityPlayer player, int amt) {
        if(amt > 0)
            setSoul(player, getSoul(player) + amt);
    }

    public static int getSoul(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getSoul();
        return 0;
    }

    public static void setSoul(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setSoul(amt);
    }

    public static void addVigor(EntityPlayer player, int amt) {
        if(amt > 0)
            setVigor(player, getVigor(player) + amt);
    }

    public static int getVigor(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getVigor();
        return 0;
    }

    public static void setVigor(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null) {
            undead.setVigor(amt);
            if(undead.getVigor() <= 0)
                player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
            else
                player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() + (getVigor(player) * .2));
        }
    }

    public static int getAttunement(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getAttunement();
        return 0;
    }

    public static void setAttunement(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setAttunement(amt);
    }

    public static int getStrength(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getStrength();
        return 0;
    }

    public static void setStrength(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setStrength(amt);
    }

    public static int getIntelligence(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getIntelligence();
        return 0;
    }

    public static void setIntelligence(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setIntelligence(amt);
    }

    public static int getFaith(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getFaith();
        return 0;
    }

    public static void setFaith(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setFaith(amt);
    }
}
