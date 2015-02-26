package palaster97.ss.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import palaster97.ss.blocks.tile.TileEntityRitual;

@SideOnly(Side.CLIENT)
public class TileEntityRitualSpecialRenderer extends TileEntitySpecialRenderer {
	
	public void renderTileEntityRitualAt(TileEntityRitual p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int p_180535_9_) {
		if((p_147500_1_ != null) && (getWorld() != null) && (p_147500_1_.getStackInSlot(0) != null)) {
			EntityItem entityitem = null;
			float ticks = Minecraft.getMinecraft().thePlayer.ticksExisted + p_147500_8_;
			GL11.glPushMatrix();
			float h = MathHelper.sin(ticks % 32767.0F / 16.0F) * 0.05F;
			// 1.15f
			GL11.glTranslatef((float)p_147500_2_ + 0.5F, (float)p_147500_4_ + .25F + h, (float)p_147500_6_ + 0.5F);
			GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
			if((p_147500_1_.getStackInSlot(0).getItem() instanceof ItemBlock)) {
				GL11.glScalef(2.0F, 2.0F, 2.0F);
			} else
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			ItemStack is = p_147500_1_.getStackInSlot(0).copy();
			is.stackSize = 1;
			entityitem = new EntityItem(getWorld(), 0.0D, 0.0D, 0.0D, is);
			entityitem.hoverStart = 0.0F;
			RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
			renderManager.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			if(!Minecraft.isFancyGraphicsEnabled()) {
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				renderManager.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			}
			GL11.glPopMatrix();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity p_180535_1_, double p_180535_2_, double p_180535_4_, double p_180535_6_, float p_180535_8_, int p_180535_9_) {
		if(p_180535_1_ instanceof TileEntityRitual)
			renderTileEntityRitualAt((TileEntityRitual) p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
	}
}
