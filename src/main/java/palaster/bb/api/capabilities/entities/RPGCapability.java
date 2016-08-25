package palaster.bb.api.capabilities.entities;

import java.util.UUID;
import java.util.concurrent.Callable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import palaster.bb.api.rpg.IRPGCareer;

public class RPGCapability {

	public static class RPGCapabilityDefault implements IRPG {
		
		public static final String TAG_STRING_CLASS = "RPGCarrerClass";
		public static final String TAG_CAREER = "RPGCareer";
		public static final String TAG_INT_CONSTITUTION = "ConstitutionInteger";
		public static final String TAG_INT_STRENGTH = "StrengthInteger";
		public static final String TAG_INT_DEFENSE = "DefenseInteger";
		public static final String TAG_INT_DEXTERITY = "DexterityInteger";
		public static final int MAX_LEVEL = 99;
		
		public static final UUID HEALTH_ID = UUID.fromString("c6531f9f-b737-4cb6-aea1-fd01c25606be");
		public static final UUID STRENGTH_ID = UUID.fromString("55d5fd28-76bd-4fa6-b5ec-b0961bad7a09");
		public static final UUID DEXTERITY_ID = UUID.fromString("d0ff0df9-9f9c-491d-9d9c-5997b5d5ba22");
		
		private IRPGCareer career;
		
		private int constitution,
		strength,
		defense,
		dexterity;
		
		@Override
		public void setConstitution(int amt) {
			if(amt > MAX_LEVEL)
				constitution = MAX_LEVEL;
			else if(amt <= 0)
				constitution = 0;
			else
				constitution = amt;
		}
		
		@Override
		public int getConstitution() { return constitution; }
		
		@Override
		public void setStrength(int amt) {
			if(amt > MAX_LEVEL)
				strength = MAX_LEVEL;
			else if(amt <= 0)
				strength = 0;
			else
				strength = amt;
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
		public void setDexterity(int amt) {
			if(amt > MAX_LEVEL)
				dexterity = MAX_LEVEL;
			else if(amt <= 0)
				dexterity = 0;
			else
				dexterity = amt;
		}
		
		@Override
		public int getDexterity() { return dexterity; }
		
		@Override
		public void changeCareer(IRPGCareer career) {
			this.career.leaveCareer();
			setCareer(career);
		}
		
		@Override
		public void setCareer(IRPGCareer career) { this.career = career; }

		@Override
		public IRPGCareer getCareer() { return career; }

		@Override
		public NBTTagCompound saveNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger(TAG_INT_CONSTITUTION, constitution);
			nbt.setInteger(TAG_INT_STRENGTH, strength);
			nbt.setInteger(TAG_INT_DEFENSE, defense);
			nbt.setInteger(TAG_INT_DEXTERITY, dexterity);
			if(career != null && career.getClass() != null && !career.getClass().getName().isEmpty()) {
				nbt.setString(TAG_STRING_CLASS, career.getClass().getName());
				nbt.setTag(TAG_CAREER, career.saveNBT());
			}
			return nbt;
		}

		@Override
		public void loadNBT(NBTTagCompound nbt) {
			constitution = nbt.getInteger(TAG_INT_CONSTITUTION);
			strength = nbt.getInteger(TAG_INT_STRENGTH);
			defense = nbt.getInteger(TAG_INT_DEFENSE);
			dexterity = nbt.getInteger(TAG_INT_DEXTERITY);
			if(!nbt.getString(TAG_STRING_CLASS).isEmpty()) {
				try {
					Object obj = Class.forName(nbt.getString(TAG_STRING_CLASS)).newInstance();
					if(obj != null && obj instanceof IRPGCareer) {
						career = (IRPGCareer) obj;
						career.loadNBT(nbt.getCompoundTag(TAG_CAREER));
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
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

	    public RPGCapabilityProvider() { rpg = new RPGCapabilityDefault(); }
	    
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
	            return (T) rpg;
	        return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return rpg.saveNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) { rpg.loadNBT(nbt); }
	}
	
	public static class RPGCapabilityStorage implements Capability.IStorage<IRPG> {
		
		@Override
		public NBTBase writeNBT(Capability<IRPG> capability, IRPG instance, EnumFacing side) { return instance.saveNBT(); }

		@Override
		public void readNBT(Capability<IRPG> capability, IRPG instance, EnumFacing side, NBTBase nbt) { instance.loadNBT((NBTTagCompound) nbt); }
	}
}
