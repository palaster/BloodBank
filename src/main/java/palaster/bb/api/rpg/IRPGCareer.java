package palaster.bb.api.rpg;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRPGCareer {
	
	void leaveCareer(@Nullable EntityPlayer player);
	
	String getUnlocalizedName();
	
	NBTTagCompound saveNBT();

    void loadNBT(NBTTagCompound nbt);
    
    @SideOnly(Side.CLIENT)
    void drawExtraInformation(GuiContainer guiContainer, @Nullable EntityPlayer player, FontRenderer fontRendererObj, int mouseX, int mouseY);
}
