package palaster.bb.api.capabilities.entities;

import java.lang.ref.SoftReference;
import java.util.UUID;
import java.util.concurrent.Callable;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import palaster.bb.api.rpg.RPGCareerBase;

public class RPGCapability {

	public static class RPGCapabilityDefault implements IRPG {
		
		public static final String TAG_STRING_CLASS = "RPGCarrerClass",
				TAG_CAREER = "RPGCareer",
				TAG_INT_CONSTITUTION = "ConstitutionInteger",
				TAG_INT_STRENGTH = "StrengthInteger",
				TAG_INT_DEFENSE = "DefenseInteger",
				TAG_INT_DEXTERITY = "DexterityInteger",
				TAG_INT_INTELLIGENCE = "IntelligenceInteger",
				TAG_INT_MAGICK = "MagickInteger";
		public static final int MAX_LEVEL = 99;
		
		public static final UUID HEALTH_ID = UUID.fromString("c6531f9f-b737-4cb6-aea1-fd01c25606be"),
				STRENGTH_ID = UUID.fromString("55d5fd28-76bd-4fa6-b5ec-b0961bad7a09"),
				DEXTERITY_ID = UUID.fromString("d0ff0df9-9f9c-491d-9d9c-5997b5d5ba22");
		
		private RPGCareerBase career;
		
		private int constitution,
		strength,
		defense,
		dexterity,
		intelligence,
		magick;
		
		@Override
		public void setConstitution(EntityPlayer player, int amt) {
			if(amt > MAX_LEVEL)
				constitution = MAX_LEVEL;
			else if(amt <= 0)
				constitution = 0;
			else
				constitution = amt;
			if(constitution <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
    			if(iAttributeInstance.getModifier(RPGCapabilityDefault.HEALTH_ID) != null)
    				iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.HEALTH_ID));
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
    			if(iAttributeInstance.getModifier(RPGCapabilityDefault.HEALTH_ID) != null)
    				iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.HEALTH_ID));
                iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.HEALTH_ID, "bb.rpg.constitution", constitution * .4, 0));
    		}
		}
		
		@Override
		public int getConstitution() { return constitution; }
		
		@Override
		public void setStrength(EntityPlayer player, int amt) {
			if(amt > MAX_LEVEL)
				strength = MAX_LEVEL;
			else if(amt <= 0)
				strength = 0;
			else
				strength = amt;
			if(strength <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
                if(iAttributeInstance.getModifier(RPGCapabilityDefault.STRENGTH_ID) != null)
                	iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.STRENGTH_ID));
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
    			if(iAttributeInstance.getModifier(RPGCapabilityDefault.STRENGTH_ID) != null)
                    iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.STRENGTH_ID));
                if(strength >= 90)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 70, 0));
                else if(strength >= 80)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 65, 0));
                else if(strength >= 70)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 60, 0));
                else if(strength >= 60)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 55, 0));
                else if(strength >= 50)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 50, 0));
                else if(strength >= 40)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 45, 0));
                else if(strength >= 30)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 40, 0));
                else if(strength >= 20)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 35, 0));
                else if(strength >= 10)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 30, 0));
                else if(strength > 0)
                	iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.STRENGTH_ID, "bb.rpg.strength", 25, 0));
    		}
		}
		
		@Override
		public int getStrength() { return strength; }
		
		@Override
		public void setDefense(int amt) {
			if(amt > MAX_LEVEL)
				defense = MAX_LEVEL;
			else if(amt <= 0)
				defense = 0;
			else
				defense = amt;
		}
		
		@Override
		public int getDefense() { return defense; }
		
		@Override
		public void setDexterity(EntityPlayer player, int amt) {
			if(amt > MAX_LEVEL)
				dexterity = MAX_LEVEL;
			else if(amt <= 0)
				dexterity = 0;
			else
				dexterity = amt;
			if(dexterity <= 0) {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
    			if(iAttributeInstance.getModifier(RPGCapabilityDefault.DEXTERITY_ID) != null)
                	iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.DEXTERITY_ID));
    		} else {
    			IAttributeInstance iAttributeInstance = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
    			if(iAttributeInstance.getModifier(RPGCapabilityDefault.DEXTERITY_ID) != null)
                	iAttributeInstance.removeModifier(iAttributeInstance.getModifier(RPGCapabilityDefault.DEXTERITY_ID));
                iAttributeInstance.applyModifier(new AttributeModifier(RPGCapabilityDefault.DEXTERITY_ID, "bb.rpg.dexterity", dexterity * .008, 0));
    		}
		}
		
		@Override
		public int getDexterity() { return dexterity; }
		
		@Override
		public void setIntelligence(int amt) {
			if(amt > MAX_LEVEL)
				intelligence = MAX_LEVEL;
			else if(amt <= 0)
				intelligence = 0;
			else
				intelligence = amt;
			
		}
		
		@Override
		public int getIntelligence() { return intelligence; }
		
		@Override
		public void setMagick(int amt) {
			if(amt > getMaxMagick())
				magick = getMaxMagick();
			else if(amt <= 0)
				magick = 0;
			else
				magick = amt;
		}

		@Override
		public int getMagick() { return magick; }

		@Override
		public int getMaxMagick() { return getIntelligence() * 1000; }
		
		@Override
		public void setCareer(EntityPlayer player, RPGCareerBase career) {
			if(player != null && this.career != null)
				this.career.leaveCareer(player);
			this.career = career;
		}

		@Override
		public RPGCareerBase getCareer() { return career; }

		@Override
		public NBTTagCompound saveNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger(TAG_INT_CONSTITUTION, constitution);
			nbt.setInteger(TAG_INT_STRENGTH, strength);
			nbt.setInteger(TAG_INT_DEFENSE, defense);
			nbt.setInteger(TAG_INT_DEXTERITY, dexterity);
			nbt.setInteger(TAG_INT_INTELLIGENCE, intelligence);
			nbt.setInteger(TAG_INT_MAGICK, magick);
			if(career != null && career.getClass() != null && !career.getClass().getName().isEmpty()) {
				nbt.setString(TAG_STRING_CLASS, career.getClass().getName());
				nbt.setTag(TAG_CAREER, career.serializeNBT());
			}
			return nbt;
		}

		@Override
		public void loadNBT(EntityPlayer player, NBTTagCompound nbt) {
			setConstitution(player, nbt.getInteger(TAG_INT_CONSTITUTION));
			setStrength(player, nbt.getInteger(TAG_INT_STRENGTH));
			setDefense(nbt.getInteger(TAG_INT_DEFENSE));
			setDexterity(player, nbt.getInteger(TAG_INT_DEXTERITY));
			setIntelligence(nbt.getInteger(TAG_INT_INTELLIGENCE));
			setMagick(nbt.getInteger(TAG_INT_MAGICK));
			if(!nbt.getString(TAG_STRING_CLASS).isEmpty()) {
				try {
					Object obj = Class.forName(nbt.getString(TAG_STRING_CLASS)).newInstance();
					if(obj != null && obj instanceof RPGCareerBase)
						if(nbt.hasKey(TAG_CAREER) && nbt.getCompoundTag(TAG_CAREER) != null)
							((RPGCareerBase) obj).deserializeNBT(nbt.getCompoundTag(TAG_CAREER));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	    public static int getExperienceCostForNextLevel(EntityPlayer player) {
	    	final IRPG rpg = RPGCapabilityProvider.get(player);
	    	if(rpg != null) {
	    		int rpgLevel = (rpg.getConstitution() + rpg.getStrength() + rpg.getDefense() + rpg.getDexterity() + rpg.getIntelligence());
	    		return rpgLevel <= 0 ? 1 : (int) (rpgLevel * 1.2);
	    	}
	        return 0;
	    }
	}
	
	public static class RPGCapabilityFactory implements Callable<IRPG> {
		@Override
	    public IRPG call() throws Exception { return new RPGCapabilityDefault(); }
	}
	
	public static class RPGCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
		
		@CapabilityInject(IRPG.class)
	    public static final Capability<IRPG> RPG_CAP = null;
		
	    protected IRPG rpg = null;

	    private final SoftReference<EntityPlayer> p;

	    public RPGCapabilityProvider(EntityPlayer player) {
	    	rpg = new RPGCapabilityDefault();
	    	p = new SoftReference<EntityPlayer>(player);
	    }
	    
	    public static IRPG get(EntityPlayer player) {
	        if(player.hasCapability(RPG_CAP, null))
	            return player.getCapability(RPG_CAP, null);
	        return null;
	    }

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return RPG_CAP != null && capability == RPG_CAP; }

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(RPG_CAP != null && capability == RPG_CAP)
	            return RPG_CAP.cast(rpg);
	        return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return rpg.saveNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			if(p != null && p.get() != null)
				rpg.loadNBT(p.get(), nbt);
		}
	}
	
	public static class RPGCapabilityStorage implements Capability.IStorage<IRPG> {
		
		@Override
		public NBTBase writeNBT(Capability<IRPG> capability, IRPG instance, EnumFacing side) { return null; }

		@Override
		public void readNBT(Capability<IRPG> capability, IRPG instance, EnumFacing side, NBTBase nbt) {}
	}
}
