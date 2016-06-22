package palaster.bb.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import palaster.bb.BloodBank;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityProvider;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapabilityDefault;
import palaster.bb.api.capabilities.entities.UndeadCapabilityProvider;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.client.SyncPlayerPropsMessage;

public class BBApi {

    private static List<Class<? extends EntityLiving>> excludeFromBloodLink = new ArrayList<Class<? extends EntityLiving>>();

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
    
    // Blood Bank Methods

    public static void consumeBlood(EntityPlayer player, int amt) {
    	if(amt > getCurrentBlood(player)) {
    		amt -= getCurrentBlood(player);
    		setCurrentBlood(player, 0);
            player.attackEntityFrom(BloodBank.proxy.bbBlood, (float) amt / 100);
    	} else
            setCurrentBlood(player, getCurrentBlood(player) - amt);
    }

    public static int getCurrentBlood(EntityPlayer player) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            return bloodBank.getCurrentBlood();
        return 0;
    }

    public static void addBlood(EntityPlayer player, int amt) {
    	if((getCurrentBlood(player) + amt) >= getMaxBlood(player))
            setCurrentBlood(player, getMaxBlood(player));
        else
            setCurrentBlood(player, getCurrentBlood(player) + amt);
    }

    public static void setCurrentBlood(EntityPlayer player, int amt) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            bloodBank.setCurrentBlood(amt);
        syncServerToClient(player);
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
        syncServerToClient(player);
    }
    
    public static boolean isLinked(EntityPlayer player) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            return bloodBank.isLinked();
        return false;
    }

    public static void linkEntity(EntityPlayer player, EntityLiving entityLiving) {
        final IBloodBank bloodBank = BloodBankCapabilityProvider.get(player);
        if(bloodBank != null)
            bloodBank.linkEntity(entityLiving);
        syncServerToClient(player);
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
        syncServerToClient(player);
    }

    public static void addFocus(EntityPlayer player, int amt) {
        if(getFocus(player) + amt <= getFocusMax(player))
            setFocus(player, getFocusMax(player));
        else
            setFocus(player, getFocus(player) + amt);
    }

    public static boolean useFocus(EntityPlayer player, int amt) {
        if(amt > getFocusMax(player) || amt > getFocus(player))
            return false;
        else if(amt <= getFocus(player)) {
            setFocus(player, getFocus(player) - amt);
            return true;
        }
        return false;
    }

    public static int getFocus(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getFocus();
        return 0;
    }

    public static void setFocus(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setFocus(amt);
        syncServerToClient(player);
    }

    public static int getFocusMax(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getFocusMax();
        return 0;
    }

    public static void setFocusMax(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            undead.setFocusMax(amt);
        syncServerToClient(player);
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
            if(undead.getVigor() <= 0) {
                IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(UndeadCapabilityDefault.healthID));
                } catch(Exception e) {}
            } else {
                IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(UndeadCapabilityDefault.healthID));
                } catch(Exception e) {}
                iAttributeInstance.applyModifier(new AttributeModifier(UndeadCapabilityDefault.healthID, "bb.vigor", getVigor(player) * .2, 0));
            }
        }
        syncServerToClient(player);
    }

    public static void addAttunement(EntityPlayer player, int amt) {
        if(amt > 0)
            setAttunement(player, getAttunement(player) + amt);
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
        syncServerToClient(player);
    }

    public static void addStrength(EntityPlayer player, int amt) {
        if(amt > 0)
            setStrength(player, getStrength(player) + amt);
    }

    public static int getStrength(EntityPlayer player) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null)
            return undead.getStrength();
        return 0;
    }

    public static void setStrength(EntityPlayer player, int amt) {
        final IUndead undead = UndeadCapabilityProvider.get(player);
        if(undead != null) {
            undead.setStrength(amt);
            if(undead.getStrength() <= 0) {
                IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(UndeadCapabilityDefault.strengthID));
                } catch(Exception e) {}
            } else {
                IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(UndeadCapabilityDefault.strengthID));
                } catch(Exception e) {}
                iAttributeInstance.applyModifier(new AttributeModifier(UndeadCapabilityDefault.strengthID, "bb.strength", undead.getStrength() * .3, 0));
            }
        }
        syncServerToClient(player);
    }

    public static void addIntelligence(EntityPlayer player, int amt) {
        if(amt > 0)
            setIntelligence(player, getIntelligence(player) + amt);
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
        syncServerToClient(player);
    }

    public static void addFaith(EntityPlayer player, int amt) {
        if(amt > 0)
            setFaith(player, getFaith(player) + amt);
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
        syncServerToClient(player);
    }

    public static int getSoulCostForNextLevel(EntityPlayer player) {
        int soulLevel = getVigor(player) + getAttunement(player) + getStrength(player) + getIntelligence(player) + getFaith(player);
        return (int)( .02 * (soulLevel ^ 3) + 3.06 * (soulLevel ^ 2) + 105.6 * soulLevel - 895);
    }

    public static void syncServerToClient(EntityPlayer player) { PacketHandler.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player); }
}
