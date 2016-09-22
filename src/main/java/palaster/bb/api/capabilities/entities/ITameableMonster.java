package palaster.bb.api.capabilities.entities;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

public interface ITameableMonster {
	
	void setOwner(UUID uuid);
	
	@Nullable
	UUID getOwner();

	NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
}
