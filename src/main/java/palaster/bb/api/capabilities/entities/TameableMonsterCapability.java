package palaster.bb.api.capabilities.entities;

import java.util.UUID;
import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class TameableMonsterCapability {

	public static class TameableMonsterCapabilityDefault implements ITameableMonster {
		
		private static final String TAG_STRING_OWNER = "TameableMonsterOwner";
		
		private UUID owner;

		@Override
		public void setOwner(UUID uuid) { owner = uuid; }

		@Override
		@Nullable
		public UUID getOwner() { return owner; }

		@Override
		public NBTTagCompound saveNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			if(owner != null)
				nbt.setUniqueId(TAG_STRING_OWNER, owner);
			return nbt;
		}

		@Override
		public void loadNBT(NBTTagCompound nbt) {
			if(nbt.hasKey(TAG_STRING_OWNER))
				owner = nbt.getUniqueId(TAG_STRING_OWNER);
		}
	}
	
	public static class TameableMonsterCapabilityFactory implements Callable<ITameableMonster> {
		@Override
	    public ITameableMonster call() throws Exception { return new TameableMonsterCapabilityDefault(); }
	}
	
	public static class TameableMonsterCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
		
		@CapabilityInject(IRPG.class)
	    public static final Capability<ITameableMonster> TAMEABLE_MONSTER_CAP = null;
		
	    protected ITameableMonster tameableMonster = null;

	    public TameableMonsterCapabilityProvider() { tameableMonster = new TameableMonsterCapabilityDefault(); }
	    
	    public static ITameableMonster get(EntityMob mob) {
	        if(mob.hasCapability(TAMEABLE_MONSTER_CAP, null))
	            return mob.getCapability(TAMEABLE_MONSTER_CAP, null);
	        return null;
	    }

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return TAMEABLE_MONSTER_CAP != null && capability == TAMEABLE_MONSTER_CAP; }

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(TAMEABLE_MONSTER_CAP != null && capability == TAMEABLE_MONSTER_CAP)
	            return (T) tameableMonster;
	        return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return tameableMonster.saveNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) { tameableMonster.loadNBT(nbt); }
	}
	
	public static class TameableMonsterCapabilityStorage implements Capability.IStorage<ITameableMonster> {
		
		@Override
		public NBTBase writeNBT(Capability<ITameableMonster> capability, ITameableMonster instance, EnumFacing side) { return instance.saveNBT(); }

		@Override
		public void readNBT(Capability<ITameableMonster> capability, ITameableMonster instance, EnumFacing side, NBTBase nbt) { instance.loadNBT((NBTTagCompound) nbt); }
	}
}
