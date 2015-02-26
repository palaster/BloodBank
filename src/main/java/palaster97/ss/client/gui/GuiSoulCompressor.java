package palaster97.ss.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.inventories.ContainerSoulCompressor;
import palaster97.ss.libs.LibResource;

@SideOnly(Side.CLIENT)
public class GuiSoulCompressor extends GuiContainer {
	
	public GuiSoulCompressor(InventoryPlayer p_i45504_1_, World worldIn) {
        this(p_i45504_1_, worldIn, BlockPos.ORIGIN);
    }

	public GuiSoulCompressor(InventoryPlayer p_i1084_1_, World p_i1084_2_, BlockPos blockPos) {
        super(new ContainerSoulCompressor(p_i1084_1_, p_i1084_2_, blockPos));
    }

	@Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        fontRendererObj.drawString(I18n.format("container.soulCompressor", new Object[0]), 28, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(LibResource.soulCompressor);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0,xSize, ySize);
    }
}
