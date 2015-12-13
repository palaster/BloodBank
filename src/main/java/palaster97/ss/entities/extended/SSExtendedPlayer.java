package palaster97.ss.entities.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import palaster97.ss.libs.LibDataWatcher;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;

public class SSExtendedPlayer implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = "SSExtendedPlayer";
	private final EntityPlayer player;

	private int bloodMax;
	
	public SSExtendedPlayer(EntityPlayer player) {
		this.player = player;
		this.bloodMax = 2000;
		this.player.getDataWatcher().addObject(LibDataWatcher.blood_network, 0);
	}
	
	public static final void register(EntityPlayer player) { player.registerExtendedProperties(SSExtendedPlayer.EXT_PROP_NAME, new SSExtendedPlayer(player)); }

	public static final SSExtendedPlayer get(EntityPlayer player) { return (SSExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME); }
	
	public void copy(SSExtendedPlayer props) {
		player.getDataWatcher().updateObject(LibDataWatcher.blood_network, props.getCurrentBlood());
		bloodMax = props.bloodMax;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound props = new NBTTagCompound();

		props.setInteger("CurrentBlood", player.getDataWatcher().getWatchableObjectInt(LibDataWatcher.blood_network));
		props.setInteger("MaxBlood", bloodMax);

		compound.setTag(EXT_PROP_NAME, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

		player.getDataWatcher().updateObject(LibDataWatcher.blood_network, props.getInteger("CurrentBlood"));
		bloodMax = props.getInteger("MaxBlood");
	}

	@Override
	public void init(Entity entity, World world) {}

	public final void consumeBlood(int amt) {
		if(amt > getCurrentBlood()) {
			int hpToTake = amt / 100;
			player.attackEntityFrom(DamageSource.magic, hpToTake);
		} else
			setCurrentBlood(getCurrentBlood() - amt);
	}

	public final int getCurrentBlood() { return player.getDataWatcher().getWatchableObjectInt(LibDataWatcher.blood_network); }

	public final void addBlood(int amt) {
		if((getCurrentBlood() + amt) >= bloodMax)
			player.getDataWatcher().updateObject(LibDataWatcher.blood_network, bloodMax);
		else
			player.getDataWatcher().updateObject(LibDataWatcher.blood_network, getCurrentBlood() + amt);
	}

	public final void setCurrentBlood(int amt) {
		if(amt >= bloodMax)
			player.getDataWatcher().updateObject(LibDataWatcher.blood_network, bloodMax);
		else
			player.getDataWatcher().updateObject(LibDataWatcher.blood_network, amt);
	}

	public final int getBloodMax () { return bloodMax; }

	public final void setBloodMax(int amt) {
		bloodMax = (amt > 0 ? amt : 0);
		sync();
	}
	
	public final void sync() { PacketHandler.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player); }
}
