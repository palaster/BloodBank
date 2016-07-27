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

public class UndeadCapability {

	public static class UndeadCapabilityDefault implements IUndead {
		
		public static String tag_isUndead = "IsUndead";
	    public static String tag_souls = "Souls";
	    public static String tag_focus = "Focus";
	    public static String tag_focusMax = "FocusMax";
	    public static String tag_vigor = "Vigor";
	    public static String tag_attunement = "Attunement";
	    public static String tag_strength = "Strength";
	    public static String tag_intelligence = "Intelligence";
	    public static String tag_faith = "Faith";
		
		private static final int maxLevel = 99;

	    public static final UUID healthID = UUID.fromString("246c351b-566e-401d-bd32-d2acbac366d4");
	    public static final UUID strengthID = UUID.fromString("9e804b09-8713-4186-a5ae-09380c674204");

	    private boolean isUndead;
	    private int souls;
	    private int focus;
	    private int focusMax;
	    private int vigor;
	    private int attunement;
	    private int strength;
	    private int intelligence; // Increase spell potency, increases sorcery, pyromancy, and dark miracle(?)
	    private int faith; // Increases Miracles(?), increase dark miracles

	    @Override
	    public boolean isUndead() { return isUndead; }

	    @Override
	    public void setUndead(boolean bool) { isUndead = bool; }

	    @Override
	    public int getSoul() {
	        if(isUndead)
	            return souls;
	        return 0;
	    }
	    
	    @Override
	    public void addSoul(int amt) { setSoul(getSoul() + amt); }

	    @Override
	    public void setSoul(int amt) {
	        if(amt <= 0)
	            souls = 0;
	        else
	            souls = amt;
	    }

	    @Override
	    public int getFocus() {
	        if(isUndead)
	            return focus;
	        return 0;
	    }
	    
	    @Override
	    public void addFocus(int amt) { setFocus(getFocus() + amt); }
	    
	    @Override
	    public void useFocus(int amt) { setFocus(getFocus() - amt); }

	    @Override
	    public void setFocus(int amt) {
	        if(amt <= 0)
	            focus = 0;
	        else
	            focus = amt;
	        if(amt >= focusMax)
	            focus = focusMax;
	    }

	    @Override
	    public int getFocusMax() { return focusMax; }

	    @Override
	    public void setFocusMax(int amt) {
	        if(amt <= 0)
	            focusMax = 0;
	        else
	            focusMax = amt;
	    }

	    @Override
	    public int getVigor() { return vigor; }

	    @Override
	    public void setVigor(int amt) {
	        if(amt <= 0)
	            vigor = 0;
	        else
	            vigor = amt;
	        if(amt >= maxLevel)
	            vigor = maxLevel;
	    }

	    @Override
	    public int getAttunement() { return attunement; }

	    @Override
	    public void setAttunement(int amt) {
	        if(amt <= 0)
	            attunement = 0;
	        else {
	            attunement = amt;
	            setFocusMax(100 + (10 * attunement));
	        }
	        if(amt >= maxLevel) {
	            attunement = maxLevel;
	            setFocusMax(100 + (10 * maxLevel));
	        }
	    }

	    @Override
	    public int getStrength() { return strength; }

	    @Override
	    public void setStrength(int amt) {
	        if(amt <= 0)
	            strength = 0;
	        else
	            strength = amt;
	        if(amt >= maxLevel)
	            strength = maxLevel;
	    }

	    @Override
	    public int getIntelligence() { return intelligence; }

	    @Override
	    public void setIntelligence(int amt) {
	        if(amt <= 0)
	            intelligence = 0;
	        else
	            intelligence = amt;
	        if(amt >= maxLevel)
	            intelligence = maxLevel;
	    }

	    @Override
	    public int getFaith() { return faith; }

	    @Override
	    public void setFaith(int amt) {
	        if(amt <= 0)
	            faith = 0;
	        else
	            faith = amt;
	        if(amt >= maxLevel)
	            faith = maxLevel;
	    }

	    @Override
	    public NBTTagCompound saveNBT() {
	        NBTTagCompound tagCompound = new NBTTagCompound();
	        tagCompound.setBoolean(tag_isUndead, isUndead);
	        tagCompound.setInteger(tag_souls, souls);
	        tagCompound.setInteger(tag_focus, focus);
	        tagCompound.setInteger(tag_focusMax, focusMax);
	        tagCompound.setInteger(tag_vigor, vigor);
	        tagCompound.setInteger(tag_attunement, attunement);
	        tagCompound.setInteger(tag_strength, strength);
	        tagCompound.setInteger(tag_intelligence, intelligence);
	        tagCompound.setInteger(tag_faith, faith);
	        return tagCompound;
	    }

	    @Override
	    public void loadNBT(NBTTagCompound nbt) {
	        isUndead = nbt.getBoolean(tag_isUndead);
	        souls = nbt.getInteger(tag_souls);
	        focus = nbt.getInteger(tag_focus);
	        focusMax = nbt.getInteger(tag_focusMax);
	        vigor = nbt.getInteger(tag_vigor);
	        attunement = nbt.getInteger(tag_attunement);
	        strength = nbt.getInteger(tag_strength);
	        intelligence = nbt.getInteger(tag_intelligence);
	        faith = nbt.getInteger(tag_faith);
	    }
	}
	
	public static class UndeadCapabilityFactory implements Callable<IUndead> {
		@Override
	    public IUndead call() throws Exception { return new UndeadCapabilityDefault(); }
	}
	
	public static class UndeadCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
		
		@CapabilityInject(IUndead.class)
	    public static final Capability<IUndead> undeadCap = null;

	    protected IUndead undead = null;

	    public UndeadCapabilityProvider() { undead = new UndeadCapabilityDefault(); }

	    public static IUndead get(EntityPlayer player) {
	        if(player.hasCapability(undeadCap, null))
	            return player.getCapability(undeadCap, null);
	        return null;
	    }

	    @Override
	    public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return undeadCap != null && capability == undeadCap; }

	    @Override
	    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	        if(undeadCap != null && capability == undeadCap)
	            return (T) undead;
	        return null;
	    }

		@Override
		public NBTTagCompound serializeNBT() { return undead.saveNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) { undead.loadNBT(nbt); }
	}
	
	public static class UndeadCapabilityStorage implements Capability.IStorage<IUndead> {
		
		@Override
	    public NBTBase writeNBT(Capability<IUndead> capability, IUndead instance, EnumFacing side) { return instance.saveNBT(); }
		
		@Override
		public void readNBT(Capability<IUndead> capability, IUndead instance, EnumFacing side, NBTBase nbt) { instance.loadNBT((NBTTagCompound) nbt); }
	}
}
