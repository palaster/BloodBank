package palaster97.ss.client.renderers;

import net.minecraft.client.Minecraft;
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

	@Override
	public void renderTileEntityAt(TileEntity p_180535_1_, double p_180535_2_, double p_180535_4_, double p_180535_6_, float p_180535_8_, int p_180535_9_) {
		TileEntityRitual ritual = (TileEntityRitual) p_180535_1_;
		if(ritual != null && ritual.getWorld() != null && ritual.getStackInSlot(0) != null) {
			EntityItem entityitem = null;
			float ticks = Minecraft.getMinecraft().thePlayer.ticksExisted + p_180535_8_;
			GL11.glPushMatrix();
			float h = MathHelper.sin(ticks % 32767.0F / 16.0F) * 0.05F;
			if(ritual.getStackInSlot(0).getItem() instanceof ItemBlock)
				GL11.glTranslatef((float)p_180535_2_ + 0.5F, (float)p_180535_4_ + .25F + h, (float)p_180535_6_ + 0.5F);
			else
				GL11.glTranslatef((float)p_180535_2_ + 0.5F, (float)p_180535_4_ + .65F + h, (float)p_180535_6_ + 0.5F);
			GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
			if(ritual.getStackInSlot(0).getItem() instanceof ItemBlock)
				GL11.glScalef(2.0F, 2.0F, 2.0F);
			else
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			ItemStack is = ritual.getStackInSlot(0).copy();
			is.stackSize = 1;
			entityitem = new EntityItem(ritual.getWorld(), 0.0D, 0.0D, 0.0D, is);
			entityitem.hoverStart = 0.0F;
			if(!Minecraft.isFancyGraphicsEnabled())
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glPopMatrix();
		}
	}
}
