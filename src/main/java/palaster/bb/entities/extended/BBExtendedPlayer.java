package palaster.bb.entities.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import palaster.bb.libs.LibDataWatcher;
import palaster.bb.network.PacketHandler;
import palaster.bb.network.client.SyncPlayerPropsMessage;

public class BBExtendedPlayer implements IExtendedEntityProperties {

	public static DamageSource bbBlood = (new DamageSource("bbBlood")).setDamageBypassesArmor().setMagicDamage();
	
	public final static String EXT_PROP_NAME = "BBExtendedPlayer";
	private final EntityPlayer player;

	private int bloodMax;
	private EntityLiving link;
	
	public BBExtendedPlayer(EntityPlayer player) {
		this.player = player;
		this.bloodMax = 0;
		this.player.getDataWatcher().addObject(LibDataWatcher.blood_network, 0);
	}
	
	public static final void register(EntityPlayer player) { player.registerExtendedProperties(EXT_PROP_NAME, new BBExtendedPlayer(player)); }

	public static final BBExtendedPlayer get(EntityPlayer player) { return (BBExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME); }
	
	public void copy(BBExtendedPlayer props) {
		player.getDataWatcher().updateObject(LibDataWatcher.blood_network, props.getCurrentBlood());
		bloodMax = props.bloodMax;
		link = props.link;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound props = new NBTTagCompound();
		props.setInteger("CurrentBlood", player.getDataWatcher().getWatchableObjectInt(LibDataWatcher.blood_network));
		props.setInteger("MaxBlood", bloodMax);
		if(link != null)
			props.setTag("LinkEntity", link.getEntityData());
		compound.setTag(EXT_PROP_NAME, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		player.getDataWatcher().updateObject(LibDataWatcher.blood_network, props.getInteger("CurrentBlood"));
		bloodMax = props.getInteger("MaxBlood");
		if(EntityList.createEntityFromNBT(props.getCompoundTag("LinkEntity"), player.worldObj) != null)
			link = (EntityLiving) EntityList.createEntityFromNBT(props.getCompoundTag("LinkEntity"), player.worldObj);
	}

	@Override
	public void init(Entity entity, World world) {}

	public final void consumeBlood(int amt) {
		if(amt > getCurrentBlood()) {
			System.out.println((float) amt / 100);
			player.attackEntityFrom(BBExtendedPlayer.bbBlood, (float) amt / 100);
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

	public final void linkEntity(EntityLiving entityLiving) {
		if(entityLiving == null)
			link = null;
		if(link == null)
			link = entityLiving;
		sync();
	}

	public final EntityLiving getLinked() { return link; }

	public final void sync() { PacketHandler.sendTo(new SyncPlayerPropsMessage(player), (EntityPlayerMP) player); }
}
