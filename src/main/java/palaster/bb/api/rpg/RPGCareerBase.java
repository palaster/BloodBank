package palaster.bb.api.rpg;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class RPGCareerBase implements INBTSerializable<NBTTagCompound> {

	public void leaveCareer(@Nullable EntityPlayer player) {}

	@Override
	public NBTTagCompound serializeNBT() { return new NBTTagCompound(); }
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {}
	
	@Override
	public String toString() { return getCareerName().isEmpty() ? "bb.career.base" : getCareerName(); }
	
	public abstract String getCareerName();

	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(GuiContainer guiContainer, @Nullable EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY) { fontRendererObj.drawString(I18n.format("bb.career.additionalInfo") + ":", 6, 90, 4210752); }
}
