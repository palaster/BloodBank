package palaster.bb.api.capabilities.worlds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class BBWorldCapability {

	public static class BBWorldCapabilityDefault implements IBBWorld {
		
		public static final String TAG_TAG_DEAD = "DeadEntityTag",
				TAG_INT_DEAD = "DeadEntityNumber";
		
		private ArrayList<NBTTagCompound> deadEntities = new ArrayList<NBTTagCompound>();
		
		@Override
		public void addDeadEntity(NBTTagCompound nbt) { deadEntities.add(nbt); }

		@Override
		public void removeDeadEntity(NBTTagCompound nbt) {
			if(deadEntities.contains(nbt))
				deadEntities.remove(nbt);
		}

		@Override
		public void clearDeadEntities(World world) { deadEntities.clear(); }

		@Override
		public List<NBTTagCompound> getDeadEntities() { return deadEntities; }

		@Override
		public NBTTagCompound getDeadEntity(int numb) { return deadEntities.get(numb); }

		@Override
		public NBTTagCompound saveNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
	        int j = 0;
	        if(!deadEntities.isEmpty())
		        for(NBTTagCompound tag : deadEntities)
		        	if(tag != null) {
		        		nbt.setTag(TAG_TAG_DEAD + j, tag);
		        		j++;
		        	}
	        nbt.setInteger(TAG_INT_DEAD, j);
			return nbt;
		}

		@Override
		public void loadNBT(NBTTagCompound nbt) {
	        for(int i = 0; i < nbt.getInteger(TAG_INT_DEAD); i++)
	        	addDeadEntity(nbt.getCompoundTag(TAG_TAG_DEAD + i));
		}
	}
	
	public static class BBWorldCapabilityFactory implements Callable<IBBWorld> {
		@Override
	    public IBBWorld call() throws Exception { return new BBWorldCapabilityDefault(); }
	}
	
	public static class BBWorldCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
		
		@CapabilityInject(IBBWorld.class)
	    public static final Capability<IBBWorld> BB_WORLD_CAP = null;
		
	    protected IBBWorld bbWorld = null;

	    public BBWorldCapabilityProvider() { bbWorld = new BBWorldCapabilityDefault(); }
	    
	    public static IBBWorld get(World world) {
	        if(world.hasCapability(BB_WORLD_CAP, null))
	            return world.getCapability(BB_WORLD_CAP, null);
	        return null;
	    }

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return BB_WORLD_CAP != null && capability == BB_WORLD_CAP; }

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(BB_WORLD_CAP != null && capability == BB_WORLD_CAP)
	            return BB_WORLD_CAP.cast(bbWorld);
	        return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return bbWorld.saveNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) { bbWorld.loadNBT(nbt); }
	}
	
	public static class BBWorldCapabilityStorage implements Capability.IStorage<IBBWorld> {
		
		@Override
		public NBTBase writeNBT(Capability<IBBWorld> capability, IBBWorld instance, EnumFacing side) { return instance.saveNBT(); }

		@Override
		public void readNBT(Capability<IBBWorld> capability, IBBWorld instance, EnumFacing side, NBTBase nbt) { instance.loadNBT((NBTTagCompound) nbt); }
	}
}
