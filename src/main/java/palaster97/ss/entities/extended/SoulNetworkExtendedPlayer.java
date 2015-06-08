package palaster97.ss.entities.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.client.SyncPlayerPropsMessage;

public class SoulNetworkExtendedPlayer implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = "SoulNetworkExtendedPlayer";
	private final EntityPlayer player;
	
	// Burning Child
	private NBTTagCompound bc;
	
	public SoulNetworkExtendedPlayer(EntityPlayer player) { this.player = player; }
	
	public static final void register(EntityPlayer player) { player.registerExtendedProperties(SoulNetworkExtendedPlayer.EXT_PROP_NAME, new SoulNetworkExtendedPlayer(player)); }

	public static final SoulNetworkExtendedPlayer get(EntityPlayer player) { return (SoulNetworkExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME); }
	
	public void copy(SoulNetworkExtendedPlayer props) {}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound props = new NBTTagCompound();
		if(bc != null)
			props.setTag("BurningChild", bc);
		compound.setTag(EXT_PROP_NAME, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		bc = (NBTTagCompound) props.getTag("BurningChild");
	}

	@Override
	public void init(Entity entity, World world) {}
	
	public final NBTTagCompound getBurningChild() { return bc; }
	
	public final void setBurningChild(NBTTagCompound nbt) {
		bc = nbt;
		sync();
	}
	
	public final void sync() { PacketHandler.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player); }
}
