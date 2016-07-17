package palaster.bb.api.capabilities.entities;

import java.lang.ref.SoftReference;
import java.util.concurrent.Callable;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import palaster.bb.libs.LibNBT;

public class BloodBankCapability {

	public static class BloodBankCapabilityDefault implements IBloodBank {
		
		private int bloodMax;
	    private int bloodCurrent;
	    private SoftReference<EntityLiving> link;
	    
		@Override
		public int consumeBlood(int amt) {
			if(getMaxBlood() > 0)
				if(amt > getCurrentBlood()) {
	        		amt -= getCurrentBlood();
	        		setCurrentBlood(0);
	        	} else {
					setCurrentBlood(getCurrentBlood() - amt);
					amt = 0;
	        	}
			return amt;
		}

		@Override
		public void addBlood(int amt) {
			if(getCurrentBlood() + amt >= getMaxBlood())
				setCurrentBlood(getMaxBlood());
			else
				setCurrentBlood(getCurrentBlood() + amt);
		}

	    @Override
	    public int getCurrentBlood() { return bloodCurrent; }

	    @Override
	    public void setCurrentBlood(int amt) {
	        if(amt >= getMaxBlood())
	            bloodCurrent = getMaxBlood();
	        else
	            bloodCurrent = amt;
	    }

	    @Override
	    public int getMaxBlood() { return bloodMax; }

	    @Override
	    public void setMaxBlood(int amt) { bloodMax = (amt > 0 ? amt : 0); }
	    
	    @Override
	    public boolean isLinked() {
	    	return link != null && link.get() != null;
	    }

	    @Override
	    public void linkEntity(EntityLiving entityLiving) {
	        if(entityLiving == null)
	            link = null;
	        if(link == null)
	            link = new SoftReference<EntityLiving>(entityLiving);
	    }

	    @Override
	    public EntityLiving getLinked() { return link.get(); }

		@Override
	    public NBTTagCompound saveNBT() {
	        NBTTagCompound tagCompound = new NBTTagCompound();
	        tagCompound.setInteger(LibNBT.number, bloodCurrent);
	        tagCompound.setInteger(LibNBT.maxNumber, bloodMax);
	        NBTTagCompound nbtTagCompound = new NBTTagCompound();
	        if(link != null && link.get() != null) {
	            link.get().writeToNBTAtomically(nbtTagCompound);
	            tagCompound.setTag(LibNBT.entityTag, nbtTagCompound);
	        }
	        return tagCompound;
	    }

	    @Override
	    public void loadNBT(NBTTagCompound nbt) {
	        if(nbt != null) {
	            bloodCurrent = nbt.getInteger(LibNBT.number);
	            bloodMax = nbt.getInteger(LibNBT.maxNumber);
	            if(nbt.getCompoundTag(LibNBT.entityTag) != null)
	                link = new SoftReference<EntityLiving>((EntityLiving) EntityList.createEntityFromNBT(nbt.getCompoundTag(LibNBT.entityTag), DimensionManager.getWorld(0)));
	        }
	    }
	}
	
	public static class BloodBankCapabilityFactory implements Callable<IBloodBank> {
		
		@Override
	    public IBloodBank call() throws Exception { return new BloodBankCapabilityDefault(); }
	}
	
	public static class BloodBankCapabilityProvider implements ICapabilitySerializable<NBTBase> {
		
		@CapabilityInject(IBloodBank.class)
	    public static final Capability<IBloodBank> bloodBankCap = null;

	    protected IBloodBank bloodBank = null;

	    public BloodBankCapabilityProvider() { bloodBank = new BloodBankCapabilityDefault(); }

	    public static IBloodBank get(EntityPlayer player) {
	        if(player.hasCapability(bloodBankCap, null))
	            return player.getCapability(bloodBankCap, null);
	        return null;
	    }

	    @Override
	    public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return bloodBankCap != null && capability == bloodBankCap; }

	    @Override
	    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	        if(bloodBankCap != null && capability == bloodBankCap)
	            return (T) bloodBank;
	        return null;
	    }

	    @Override
	    public NBTBase serializeNBT() { return bloodBank.saveNBT(); }

	    @Override
	    public void deserializeNBT(NBTBase nbt) { bloodBank.loadNBT((NBTTagCompound) nbt); }
	}
	
	public static class BloodBankCapabilityStorage implements Capability.IStorage<IBloodBank> {
		
	    @Override
	    public NBTBase writeNBT(Capability<IBloodBank> capability, IBloodBank instance, EnumFacing side) { return instance.saveNBT(); }

	    @Override
	    public void readNBT(Capability<IBloodBank> capability, IBloodBank instance, EnumFacing side, NBTBase nbt) { instance.loadNBT((NBTTagCompound) nbt); }
	}
}
