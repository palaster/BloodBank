package palaster.bb.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.entities.careers.CareerUndead;

public class BBApi {

    private static final List<Class<? extends EntityLiving>> excludeFromBloodLink = new ArrayList<Class<? extends EntityLiving>>();
    private static final List<Class<? extends EntityLiving>> bossToken = new ArrayList<Class<? extends EntityLiving>>();
    private static final List<ItemStack> itemToken = new ArrayList<ItemStack>();

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
    
    public static void addBossToToken(EntityLiving el) {
        if(el != null) {
            for(Class<? extends EntityLiving> classEL : bossToken)
                if(classEL.getName().equals(el.getClass().getName())) {
                    System.out.println(el.getClass().getName() + " has already been added.");
                    return;
                }
            bossToken.add(el.getClass());
        }
    }

    public static void addBossClassToToken(Class<? extends EntityLiving> classELTemp) {
        if(classELTemp != null) {
            for(Class<? extends EntityLiving> classEL : bossToken)
                if(classEL.getName().equals(classELTemp.getName())) {
                    System.out.println(classELTemp.getName() + " has already been added.");
                    return;
                }
            bossToken.add(classELTemp);
        }
    }
    
    public static Class<? extends EntityLiving> getBossFromToken(int id) {
    	if(bossToken.size() >= id)
    		return bossToken.get(id);
    	return null;
    }
    
    public static boolean checkTokensForBoss(Class<? extends  EntityLiving> classELTemp) {
        for(Class<? extends EntityLiving> classEL : bossToken)
            if(classEL.getName().equals(classELTemp.getName()))
                return true;
        return false;
    }
    
    public static int getSizeBossTokens() { return bossToken.size(); }
    
    public static void addItemStackToToken(ItemStack itemStack) {
        if(itemStack != null) {
            for(ItemStack stack : itemToken)
                if(stack.getItem() == itemStack.getItem() && stack.getItemDamage() == itemStack.getItemDamage()) {
                    System.out.println(stack.getDisplayName() + " has already been added.");
                    return;
                }
            itemToken.add(itemStack);
        }
    }
    
    public static ItemStack getItemStackFromToken(int id) {
    	if(itemToken.size() >= id)
    		return itemToken.get(id);
    	return null;
    }
    
    public static boolean checkTokensForItemStack(ItemStack itemStack) {
        for(ItemStack stack : itemToken)
            if(stack.getItem() == itemStack.getItem() && stack.getItemDamage() == itemStack.getItemDamage())
                return true;
        return false;
    }
    
    public static int getSizeItemTokens() { return itemToken.size(); }

    // Undead Methods
    
    public static void recalculateVigorBoost(EntityPlayer player) {
    	final IRPG rpg = RPGCapabilityProvider.get(player);
    	if(rpg != null)
    		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead) {
    			if(((CareerUndead) rpg.getCareer()).getVigor() <= 0) {
                    IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                    try {
                        iAttributeInstance.removeModifier(iAttributeInstance.getModifier(CareerUndead.healthID));
                    } catch(Exception e) {}
                } else {
                    IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                    try {
                        iAttributeInstance.removeModifier(iAttributeInstance.getModifier(CareerUndead.healthID));
                    } catch(Exception e) {}
                    iAttributeInstance.applyModifier(new AttributeModifier(CareerUndead.healthID, "bb.vigor", ((CareerUndead) rpg.getCareer()).getVigor() * .2, 0));
                }
    		}
    }
    
    public static void recalculateStrengthBoost(EntityPlayer player) {
    	final IRPG rpg = RPGCapabilityProvider.get(player);
        if(rpg != null)
        	if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead) {
        		if(((CareerUndead) rpg.getCareer()).getStrength() <= 0) {
                    IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
                    try {
                        iAttributeInstance.removeModifier(iAttributeInstance.getModifier(CareerUndead.strengthID));
                    } catch(Exception e) {}
                } else {
                    IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
                    try {
                        iAttributeInstance.removeModifier(iAttributeInstance.getModifier(CareerUndead.strengthID));
                    } catch(Exception e) {}
                    iAttributeInstance.applyModifier(new AttributeModifier(CareerUndead.strengthID, "bb.strength", ((CareerUndead) rpg.getCareer()).getStrength() * .9, 0));
                }
        	}
    }

    public static int getSoulCostForNextLevel(EntityPlayer player) {
    	final IRPG rpg = RPGCapabilityProvider.get(player);
    	if(rpg != null) {
    		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUndead) {
    			int soulLevel = ((CareerUndead) rpg.getCareer()).getVigor() + ((CareerUndead) rpg.getCareer()).getAttunement() + ((CareerUndead) rpg.getCareer()).getStrength() + ((CareerUndead) rpg.getCareer()).getIntelligence() + ((CareerUndead) rpg.getCareer()).getFaith();
                return (int)( .02 * (soulLevel ^ 3) + 3.06 * (soulLevel ^ 2) + 105.6 * soulLevel - 895);
    		}
    	}
        return 0;
    }
}
