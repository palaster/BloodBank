package palaster.bb.entities.careers;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.rpg.RPGCareerBase;

public class CareerBloodSorcerer extends RPGCareerBase {
	
	public static final String TAG_INT_BLOOD_CURRENT = "BloodCurrent";
	public static final String TAG_INT_BLOOD_MAX = "BloodMax";
	
	private int bloodMax;
    private int bloodCurrent;
    
    public CareerBloodSorcerer() { this(2000, 0); }
    
    public CareerBloodSorcerer(int bloodMax, int bloodCurrent) {
    	super();
    	this.bloodMax = bloodMax;
    	this.bloodCurrent = bloodCurrent;
    }
    
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

    @Override
    public String getUnlocalizedName() { return "bb.career.bloodSorcerer"; }

	@Override
    public NBTTagCompound saveNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(TAG_INT_BLOOD_CURRENT, bloodCurrent);
        tagCompound.setInteger(TAG_INT_BLOOD_MAX, bloodMax);
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        bloodCurrent = nbt.getInteger(TAG_INT_BLOOD_CURRENT);
        bloodMax = nbt.getInteger(TAG_INT_BLOOD_MAX);
    }
    
    @Override
    public void drawExtraInformation(GuiContainer guiContainer, @Nullable EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY) {
    	super.drawExtraInformation(guiContainer, player, fontRendererObj, mouseX, mouseY);
    	if(player != null) {
	    	IRPG rpg = RPGCapabilityProvider.get(player);
	    	if(rpg != null && rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer)
	    		fontRendererObj.drawString(I18n.format("bb.jei.blood") + ": " + ((CareerBloodSorcerer) rpg.getCareer()).getCurrentBlood() + "/" + ((CareerBloodSorcerer) rpg.getCareer()).getMaxBlood(), 6, 100, 4210752);
    	}
    }
}
