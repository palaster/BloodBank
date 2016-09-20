package palaster.bb.entities.careers;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.rpg.RPGCareerBase;

public class CareerUnkindled extends RPGCareerBase {
	
    public static final String TAG_INT_SOULS = "Souls";
    public static final String TAG_INT_FOCUS = "Focus";
    public static final String TAG_INT_FOCUS_MAX = "FocusMax";

    private int souls;
    private int focus;
    private int focusMax;
    
    public CareerUnkindled() { this(0, 0, 0); }
    
    public CareerUnkindled(int souls, int focus, int focusMax) {
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
    public String getUnlocalizedName() { return "bb.career.unkindled"; }

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
    
    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtraInformation(GuiContainer guiContainer, EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY) {
    	super.drawExtraInformation(guiContainer, player, fontRendererObj, mouseX, mouseY);
    	IRPG rpg = RPGCapabilityProvider.get(player);
    	if(rpg != null && rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled) {
    		fontRendererObj.drawString(I18n.format("bb.undead.soul") + ": " + ((CareerUnkindled) rpg.getCareer()).getSoul(), 6, 90, 4210752);
    		fontRendererObj.drawString(I18n.format("bb.undead.focus") + ": " + ((CareerUnkindled) rpg.getCareer()).getFocus() + " / " + ((CareerUnkindled) rpg.getCareer()).getFocusMax(), 6, 100, 4210752);
    	}
    }
}
