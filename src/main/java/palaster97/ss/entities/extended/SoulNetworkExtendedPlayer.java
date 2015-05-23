package palaster97.ss.entities.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;
import palaster97.ss.runes.Rune;

public class SoulNetworkExtendedPlayer implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = "SoulNetworkExtendedPlayer";
	private final EntityPlayer player;
	
	// Class none 0, staff 1, mirror 2
	private int classID;
	
	// Runer
	private boolean isRuneCharged;
	private Rune rune;
	
	// Burning Child
	private NBTTagCompound bc;
	
	public SoulNetworkExtendedPlayer(EntityPlayer player) { this.player = player; }
	
	public static final void register(EntityPlayer player) { player.registerExtendedProperties(SoulNetworkExtendedPlayer.EXT_PROP_NAME, new SoulNetworkExtendedPlayer(player)); }

	public static final SoulNetworkExtendedPlayer get(EntityPlayer player) { return (SoulNetworkExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME); }
	
	public void copy(SoulNetworkExtendedPlayer props) {}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound props = new NBTTagCompound();
		props.setInteger("ClassID", classID);
		props.setBoolean("IsRuneCharged", isRuneCharged);
		if(rune != null)
			props.setInteger("RuneID", rune.runeID);
		if(bc != null)
			props.setTag("BurningChild", bc);
		compound.setTag(EXT_PROP_NAME, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		classID = props.getInteger("ClassID");
		isRuneCharged = props.getBoolean("IsRuneCharged");
		if(Rune.runes[props.getInteger("RuneID")] != null)
			rune = Rune.runes[props.getInteger("RuneID")];
		bc = (NBTTagCompound) props.getTag("BurningChild");
	}

	@Override
	public void init(Entity entity, World world) {}
	
	public final int getClassID() { return classID; }
	
	public final void setClassID(int value) {
		classID = value;
		sync();
	}

	public final boolean getRuneCharge() { return isRuneCharged; }
	
	public final void setRuneCharge(boolean value) {
		isRuneCharged = value;
		sync();
	}
	
	public final Rune getRune() { return rune; }
	
	public final void setRune(int runeID) {
		if(Rune.runes[runeID] != null)
			rune = Rune.runes[runeID];
		sync();
	}
	
	public final void removeRune() {
		rune = null;
		sync();
	}
	
	public final NBTTagCompound getBurningChild() { return bc; }
	
	public final void setBurningChild(NBTTagCompound nbt) {
		bc = nbt;
		sync();
	}
	
	public final void sync() { PacketHandler.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player); }
}
