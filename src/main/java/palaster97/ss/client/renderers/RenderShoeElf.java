package palaster97.ss.client.renderers;

import palaster97.ss.entities.EntityShoeElf;
import palaster97.ss.libs.LibResource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShoeElf extends RenderLiving {

	public RenderShoeElf(RenderManager p_i46153_1_, ModelBase p_i46153_2_, float p_i46153_3_) {
		super(p_i46153_1_, p_i46153_2_, p_i46153_3_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		if(p_110775_1_ instanceof EntityShoeElf)
			return LibResource.shoeElf;
		return LibResource.lp;
	}
}
