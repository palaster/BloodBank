package palaster97.ss.entities.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import palaster97.ss.inventories.InventorySpace;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;
import palaster97.ss.runes.Rune;

public class SoulNetworkExtendedPlayer implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = "SoulNetworkExtendedPlayer";
	private final InventorySpace space = new InventorySpace();
	private final EntityPlayer player;
	private boolean isRuneCharged;
	private Rune rune;
	private NBTTagCompound bc;
	private NBTTagCompound[] legion = new NBTTagCompound[8];
	
	public SoulNetworkExtendedPlayer(EntityPlayer player) { this.player = player; }
	
	public static final void register(EntityPlayer player) { player.registerExtendedProperties(SoulNetworkExtendedPlayer.EXT_PROP_NAME, new SoulNetworkExtendedPlayer(player)); }

	public static final SoulNetworkExtendedPlayer get(EntityPlayer player) { return (SoulNetworkExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME); }
	
	public void copy(SoulNetworkExtendedPlayer props) {
		space.copy(props.space);
		rune = props.rune;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound props = new NBTTagCompound();
		space.writeToNBT(props);
		props.setBoolean("IsRuneCharged", isRuneCharged);
		if(rune != null)
			props.setInteger("RuneID", rune.runeID);
		if(bc != null)
			props.setTag("BurningChild", bc);
		int temp = 0;
		for(int i = 0; i < legion.length; i++)
			if(legion[i] != null) {
				props.setTag("Legion_" + i, legion[i]);
				temp++;
			}
		props.setInteger("Legion Count", temp);
		compound.setTag(EXT_PROP_NAME, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		space.readFromNBT(props);
		isRuneCharged = props.getBoolean("IsRuneCharged");
		if(Rune.runes[props.getInteger("RuneID")] != null)
			rune = Rune.runes[props.getInteger("RuneID")];
		bc = (NBTTagCompound) props.getTag("BurningChild");
		int temp = props.getInteger("Legion Count");
		for(int i = 0; i < temp; i++)
			legion[i] = (NBTTagCompound) props.getTag("Legion_" + i);
	}

	@Override
	public void init(Entity entity, World world) {}
	
	public final InventorySpace getSpace() { return space; }

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
