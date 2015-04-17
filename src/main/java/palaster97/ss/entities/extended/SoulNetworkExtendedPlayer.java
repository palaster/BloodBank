package palaster97.ss.entities.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import palaster97.ss.inventories.InventorySpace;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;
import palaster97.ss.rituals.Ritual;
import palaster97.ss.rituals.RitualActive;
import palaster97.ss.runes.Rune;

public class SoulNetworkExtendedPlayer implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = "SoulNetworkExtendedPlayer";
	private final InventorySpace space = new InventorySpace();
	private final EntityPlayer player;
	private RitualActive[] activeRituals = new RitualActive[10];
	private boolean isRuneCharged;
	private Rune rune;
	
	public SoulNetworkExtendedPlayer(EntityPlayer player) { this.player = player; }
	
	public static final void register(EntityPlayer player) { player.registerExtendedProperties(SoulNetworkExtendedPlayer.EXT_PROP_NAME, new SoulNetworkExtendedPlayer(player)); }

	public static final SoulNetworkExtendedPlayer get(EntityPlayer player) { return (SoulNetworkExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME); }
	
	public void copy(SoulNetworkExtendedPlayer props) {
		space.copy(props.space);
		activeRituals = props.activeRituals;
		rune = props.rune;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound props = new NBTTagCompound();
		space.writeToNBT(props);
		props.setInteger("AcitveRitualAmt", activeRituals.length);
		for(int i = 0; i < activeRituals.length; i++)
			if(activeRituals[i] != null)
				props.setInteger("ActiveRitual" + i, activeRituals[i].ritualID);
		props.setBoolean("IsRuneCharged", isRuneCharged);
		if(rune != null)
			props.setInteger("RuneID", rune.runeID);
		compound.setTag(EXT_PROP_NAME, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		space.readFromNBT(props);
		for(int i = 0; i < props.getInteger("ActiveRitualAmt"); i++) {
			if(Ritual.rituals[props.getInteger("ActiveRitual" + i)] instanceof RitualActive)
				activeRituals[i] = (RitualActive) Ritual.rituals[props.getInteger("ActiveRitual" + i)];
		}
		isRuneCharged = props.getBoolean("IsRuneCharged");
		if(Rune.runes[props.getInteger("RuneID")] != null)
			rune = Rune.runes[props.getInteger("RuneID")];
	}

	@Override
	public void init(Entity entity, World world) {}
	
	public final InventorySpace getSpace() { return space; }

	public final boolean canAddRitual() {
		for(int i = 0; i < activeRituals.length; i++)
			if(activeRituals[i] == null)
				return true;
		return false;
	}
	
	public final void addRitual(Ritual ritual) {
		for(int i = 0; i < activeRituals.length; i++) {
			if(activeRituals[i] == null) {
				if(ritual instanceof RitualActive)
					activeRituals[i] = (RitualActive) ritual;
			}
		}
		sync();
	}
	
	public final void removeRitual(int ritualID, BlockPos pos) {
		if(Ritual.rituals[ritualID] != null) {
			if(Ritual.rituals[ritualID] instanceof RitualActive)
				for(int i = 0; i < activeRituals.length; i++)
					if(activeRituals[i] != null && activeRituals[i].ritualPos == pos)
						activeRituals[i] = null;
		}
		sync();
	}
	
	public final RitualActive[] getActives() { return activeRituals; }
	
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
	
	public final void sync() { PacketHandler.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player); }
}
