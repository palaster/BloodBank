package palaster.bb.api.rpg;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class RPGCareerBase implements IRPGCareer {
	
	@Override
	public void leaveCareer() {}
	
	@Override
	public String getUnlocalizedName() { return "bb.career.base"; }

	@Override
	public NBTTagCompound saveNBT() { return new NBTTagCompound(); }

	@Override
	public void loadNBT(NBTTagCompound nbt) {}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtraInformation(GuiContainer guiContainer, EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("bb.career.additionalInfo") + ":", 6, 80, 4210752);
	}
}
