package palaster.bb.entities.careers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.rpg.RPGCareerBase;

public class CareerWorkshopWitch extends RPGCareerBase {

	public static final String TAG_BOOLEAN_LEADER = "WorkShopWitchIsLeader";
	public static final String TAG_UUID_LEADER = "WorkShopWitchLeaderUUID";
	
	private boolean isLeader;
	private UUID leader;
	
	public CareerWorkshopWitch() { this(false); }
	
	public CareerWorkshopWitch(boolean isLeader) { this.isLeader = isLeader; }
	
	public void setIsLeader(boolean isLeader) { this.isLeader = isLeader; }
	
	public boolean isLeader() { return isLeader; }
	
	public void setLeader(UUID uuid) { this.leader = uuid; }
	
	@Nullable
	public UUID getLeader() { return leader; }

	@Override
	public String getUnlocalizedName() { return "bb.career.workshopWitch"; }
	
	@Override
	public NBTTagCompound saveNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean(TAG_BOOLEAN_LEADER, isLeader);
		if(!isLeader && leader != null)
			nbt.setUniqueId(TAG_UUID_LEADER, leader);
		return nbt;
	}
	
	@Override
	public void loadNBT(NBTTagCompound nbt) {
		isLeader = nbt.getBoolean(TAG_BOOLEAN_LEADER);
		if(nbt.hasKey(TAG_UUID_LEADER))
			leader = nbt.getUniqueId(TAG_UUID_LEADER);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(GuiContainer guiContainer, EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY) {
		super.drawExtraInformation(guiContainer, player, fontRendererObj, mouseX, mouseY);
		if(isLeader) {} else
			fontRendererObj.drawString(I18n.format("bb.career.workshopWitch.leader") + ":" + (leader == null ? "" : player.worldObj.getPlayerEntityByUUID(getLeader()).getDisplayName()), 6, 100, 4210752);
	}
}
