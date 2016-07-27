package palaster.bb.entities.careers;

import java.lang.ref.SoftReference;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import palaster.bb.api.rpg.RPGCareerBase;

public class CareerBloodSorcerer extends RPGCareerBase {
	
	public static String tag_bloodCurrent = "BloodCurrent";
	public static String tag_bloodMax = "BloodMax";
	public static String tag_linkEntity = "LinkedEntity";
	
	private int bloodMax;
    private int bloodCurrent;
    private SoftReference<EntityLiving> link;
    
	public boolean isBloodSorcerer() { return getMaxBlood() > 0; }
    
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

	public void addBlood(int amt) {
		if(getCurrentBlood() + amt >= getMaxBlood())
			setCurrentBlood(getMaxBlood());
		else
			setCurrentBlood(getCurrentBlood() + amt);
	}

    public int getCurrentBlood() { return bloodCurrent; }

    public void setCurrentBlood(int amt) {
        if(amt >= getMaxBlood())
            bloodCurrent = getMaxBlood();
        else
            bloodCurrent = amt;
    }

    public int getMaxBlood() { return bloodMax; }

    public void setMaxBlood(int amt) { bloodMax = (amt > 0 ? amt : 0); }
    
    public boolean isLinked() {
    	return link != null && link.get() != null;
    }

    public void linkEntity(EntityLiving entityLiving) {
        if(entityLiving == null)
            link = null;
        if(link == null)
            link = new SoftReference<EntityLiving>(entityLiving);
    }

    public EntityLiving getLinked() { return link.get(); }

	@Override
    public NBTTagCompound saveNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(tag_bloodCurrent, bloodCurrent);
        tagCompound.setInteger(tag_bloodMax, bloodMax);
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        if(link != null && link.get() != null) {
            link.get().writeToNBTAtomically(nbtTagCompound);
            tagCompound.setTag(tag_linkEntity, nbtTagCompound);
        }
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        bloodCurrent = nbt.getInteger(tag_bloodCurrent);
        bloodMax = nbt.getInteger(tag_bloodMax);
        if(nbt.getCompoundTag(tag_linkEntity) != null)
            link = new SoftReference<EntityLiving>((EntityLiving) EntityList.createEntityFromNBT(nbt.getCompoundTag(tag_linkEntity), DimensionManager.getWorld(0)));
    }
}
