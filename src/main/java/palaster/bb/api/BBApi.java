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
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityDefault;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;

public class BBApi {

    private static final List<Class<? extends EntityLiving>> EXCLUDE_FROM_BLOOD_LINK = new ArrayList<Class<? extends EntityLiving>>();
    private static final List<Class<? extends EntityLiving>> BOSS_TOKEN = new ArrayList<Class<? extends EntityLiving>>();
    private static final List<ItemStack> ITEM_TOKEN = new ArrayList<ItemStack>();

    public static void addEntityLivingToExclude(EntityLiving el) {
        if(el != null) {
            for(Class<? extends EntityLiving> classEL : EXCLUDE_FROM_BLOOD_LINK)
                if(classEL.getName().equals(el.getClass().getName())) {
                    System.out.println(el.getClass().getName() + " has already been excluded.");
                    return;
                }
            EXCLUDE_FROM_BLOOD_LINK.add(el.getClass());
        }
    }

    public static void addEntityLivingClassToExclude(Class<? extends EntityLiving> classELTemp) {
        if(classELTemp != null) {
            for(Class<? extends EntityLiving> classEL : EXCLUDE_FROM_BLOOD_LINK)
                if(classEL.getName().equals(classELTemp.getName())) {
                    System.out.println(classELTemp.getName() + " has already been excluded.");
                    return;
                }
            EXCLUDE_FROM_BLOOD_LINK.add(classELTemp);
        }
    }

    public static boolean checkExcludesForEntity(EntityLiving el) {
        for(Class<? extends EntityLiving> classEL : EXCLUDE_FROM_BLOOD_LINK)
            if(classEL.getName().equals(el.getClass().getName()))
                return true;
        return false;
    }

    public static boolean checkExcludesForClass(Class<? extends  EntityLiving> classELTemp) {
        for(Class<? extends EntityLiving> classEL : EXCLUDE_FROM_BLOOD_LINK)
            if(classEL.getName().equals(classELTemp.getName()))
                return true;
        return false;
    }
    
    public static void addBossToToken(EntityLiving el) {
        if(el != null) {
            for(Class<? extends EntityLiving> classEL : BOSS_TOKEN)
                if(classEL.getName().equals(el.getClass().getName())) {
                    System.out.println(el.getClass().getName() + " has already been added.");
                    return;
                }
            BOSS_TOKEN.add(el.getClass());
        }
    }

    public static void addBossClassToToken(Class<? extends EntityLiving> classELTemp) {
        if(classELTemp != null) {
            for(Class<? extends EntityLiving> classEL : BOSS_TOKEN)
                if(classEL.getName().equals(classELTemp.getName())) {
                    System.out.println(classELTemp.getName() + " has already been added.");
                    return;
                }
            BOSS_TOKEN.add(classELTemp);
        }
    }
    
    public static Class<? extends EntityLiving> getBossFromToken(int id) {
    	if(BOSS_TOKEN.size() >= id)
    		return BOSS_TOKEN.get(id);
    	return null;
    }
    
    public static boolean checkTokensForBoss(Class<? extends  EntityLiving> classELTemp) {
        for(Class<? extends EntityLiving> classEL : BOSS_TOKEN)
            if(classEL.getName().equals(classELTemp.getName()))
                return true;
        return false;
    }
    
    public static int getSizeBossTokens() { return BOSS_TOKEN.size(); }
    
    public static void addItemStackToToken(ItemStack itemStack) {
        if(itemStack != null) {
            for(ItemStack stack : ITEM_TOKEN)
                if(stack.getItem() == itemStack.getItem() && stack.getItemDamage() == itemStack.getItemDamage()) {
                    System.out.println(stack.getDisplayName() + " has already been added.");
                    return;
                }
            ITEM_TOKEN.add(itemStack);
        }
    }
    
    public static ItemStack getItemStackFromToken(int id) {
    	if(ITEM_TOKEN.size() >= id)
    		return ITEM_TOKEN.get(id);
    	return null;
    }
    
    public static boolean checkTokensForItemStack(ItemStack itemStack) {
        for(ItemStack stack : ITEM_TOKEN)
            if(stack.getItem() == itemStack.getItem() && stack.getItemDamage() == itemStack.getItemDamage())
                return true;
        return false;
    }
    
    public static int getSizeItemTokens() { return ITEM_TOKEN.size(); }

    // Undead Methods
    
    public static void calculateConstitutionBoost(EntityPlayer player) {
    	final IRPG rpg = RPGCapabilityProvider.get(player);
    	if(rpg != null) {
    		if(rpg.getConstitution() <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.HEALTH_ID));
                } catch(Exception e) {}
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.HEALTH_ID));
                } catch(Exception e) {}
                iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.HEALTH_ID, "bb.rpg.constitution", rpg.getConstitution() * .4, 0));
    		}
    	}
    }
    
    public static void calculateStrengthBoost(EntityPlayer player) {
    	final IRPG rpg = RPGCapabilityProvider.get(player);
    	if(rpg != null) {
    		if(rpg.getStrength() <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.STRENGTH_ID));
                } catch(Exception e) {}
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.STRENGTH_ID));
                } catch(Exception e) {}
                iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", rpg.getStrength() * .9, 0));
    		}
    	}
    }
    
    public static void calculateDexterityBoost(EntityPlayer player) {
    	final IRPG rpg = RPGCapabilityProvider.get(player);
    	if(rpg != null) {
    		if(rpg.getDexterity() <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
                try {
                	iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.DEXTERITY_ID));
                } catch(Exception e) {}
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
                try {
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.DEXTERITY_ID));
                } catch(Exception e) {}
                iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.DEXTERITY_ID, "bb.rpg.dexterity", rpg.getDexterity() * .008, 0));
    		}
    	}
    }
    
    public static int getExperienceCostForNextLevel(EntityPlayer player) {
    	final IRPG rpg = RPGCapabilityProvider.get(player);
    	if(rpg != null) {
    		int rpgLevel = (rpg.getConstitution() + rpg.getStrength() + rpg.getDefense() + rpg.getDexterity());
    		return rpgLevel <= 0 ? 1 : (int) (rpgLevel * 1.2);
    	}
        return 0;
    }
}
