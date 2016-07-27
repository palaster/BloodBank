package palaster.bb.api.capabilities.entities;

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
		
		private IRPGCareer career;

		@Override
		public IRPGCareer getCareer() { return career; }

		@Override
		public void setCareer(IRPGCareer career) { this.career = career; }

		@Override
		public NBTTagCompound saveNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			if(career != null && career.getClass() != null && !career.getClass().getName().isEmpty()) {
				nbt.setString(career_class_name, career.getClass().getName());
				nbt.setTag(tag_career, career.saveNBT());
			}
			return nbt;
		}

		@Override
		public void loadNBT(NBTTagCompound nbt) {
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
