package palaster.bb.entities.careers;

import net.minecraft.nbt.NBTTagCompound;
import palaster.bb.api.rpg.RPGCareerBase;

public class CareerUndead extends RPGCareerBase {
	
    public static final String TAG_INT_SOULS = "Souls";
    public static final String TAG_INT_FOCUS = "Focus";
    public static final String TAG_INT_FOCUS_MAX = "FocusMax";

    private int souls;
    private int focus;
    private int focusMax;
    
    public CareerUndead() { this(0, 0, 0); }
    
    public CareerUndead(int souls, int focus, int focusMax) {
    	this.souls = souls;
    	this.focus = focus;
    	this.focusMax = focusMax;
    }

    public int getSoul() { return souls; }
    
    public void addSoul(int amt) { setSoul(getSoul() + amt); }

    public void setSoul(int amt) {
        if(amt <= 0)
            souls = 0;
        else
            souls = amt;
    }

    public int getFocus() { return focus; }
    
    public void addFocus(int amt) { setFocus(getFocus() + amt); }
    
    public void useFocus(int amt) { setFocus(getFocus() - amt); }

    public void setFocus(int amt) {
        if(amt <= 0)
            focus = 0;
        else
            focus = amt;
        if(amt >= focusMax)
            focus = focusMax;
    }

    public int getFocusMax() { return focusMax; }

    public void setFocusMax(int amt) {
        if(amt <= 0)
            focusMax = 0;
        else
            focusMax = amt;
    }
    
    @Override
    public String getUnlocalizedName() { return "bb.career.undead"; }

    @Override
    public NBTTagCompound saveNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(TAG_INT_SOULS, souls);
        tagCompound.setInteger(TAG_INT_FOCUS, focus);
        tagCompound.setInteger(TAG_INT_FOCUS_MAX, focusMax);
        return tagCompound;
    }

    @Override
    public void loadNBT(NBTTagCompound nbt) {
        souls = nbt.getInteger(TAG_INT_SOULS);
        focus = nbt.getInteger(TAG_INT_FOCUS);
        focusMax = nbt.getInteger(TAG_INT_FOCUS_MAX);
    }
}
