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
		
		public static String career_class_name = "RPGCarrerClass";
		public static String tag_career = "RPGCareer";
		public static String tag_intConstitution = "ConstitutionInteger";
		public static String tag_intStrength = "StrengthInteger";
		public static String tag_intDefense = "DefenseInteger";
		public static String tag_intDexterity = "DexterityInteger";
		public static int maxLevel = 99;
		
		public static final UUID healthID = UUID.fromString("c6531f9f-b737-4cb6-aea1-fd01c25606be");
		public static final UUID strengthID = UUID.fromString("55d5fd28-76bd-4fa6-b5ec-b0961bad7a09");
		public static final UUID dexterityID = UUID.fromString("d0ff0df9-9f9c-491d-9d9c-5997b5d5ba22");
		
		private IRPGCareer career;
		
		private int constitution,
		strength,
		defense,
		dexterity;
		
		@Override
		public void setConstitution(int amt) {
			if(amt > maxLevel)
				constitution = maxLevel;
			else if(amt <= 0)
				constitution = 0;
			else
				constitution = amt;
		}
		
		@Override
		public int getConstitution() { return constitution; }
		
		@Override
		public void setStrength(int amt) {
			if(amt > maxLevel)
				strength = maxLevel;
			else if(amt <= 0)
				strength = 0;
			else
				strength = amt;
		}
		
		@Override
		public int getStrength() { return strength; }
		
		@Override
		public void setDefense(int amt) {
			if(amt > maxLevel)
				defense = maxLevel;
			else if(amt <= 0)
				defense = 0;
			else
				defense = amt;
		}
		
		@Override
		public int getDefense() { return defense; }
		
		@Override
		public void setDexterity(int amt) {
			if(amt > maxLevel)
				dexterity = maxLevel;
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
			nbt.setInteger(tag_intConstitution, constitution);
			nbt.setInteger(tag_intStrength, strength);
			nbt.setInteger(tag_intDefense, defense);
			nbt.setInteger(tag_intDexterity, dexterity);
			if(career != null && career.getClass() != null && !career.getClass().getName().isEmpty()) {
				nbt.setString(career_class_name, career.getClass().getName());
				nbt.setTag(tag_career, career.saveNBT());
			}
			return nbt;
		}

		@Override
		public void loadNBT(NBTTagCompound nbt) {
			constitution = nbt.getInteger(tag_intConstitution);
			strength = nbt.getInteger(tag_intStrength);
			defense = nbt.getInteger(tag_intDefense);
			dexterity = nbt.getInteger(tag_intDexterity);
			if(!nbt.getString(career_class_name).isEmpty()) {
				try {
					Object obj = Class.forName(nbt.getString(career_class_name)).newInstance();
					if(obj != null && obj instanceof IRPGCareer) {
						career = (IRPGCareer) obj;
						career.loadNBT(nbt.getCompoundTag(tag_career));
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
	    public static final Capability<IRPG> rpgCap = null;
		
	    protected IRPG rpg = null;

	    public RPGCapabilityProvider() { rpg = new RPGCapabilityDefault(); }
	    
	    public static IRPG get(EntityPlayer player) {
	        if(player.hasCapability(rpgCap, null))
	            return player.getCapability(rpgCap, null);
	        return null;
	    }

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return rpgCap != null && capability == rpgCap; }

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(rpgCap != null && capability == rpgCap)
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
