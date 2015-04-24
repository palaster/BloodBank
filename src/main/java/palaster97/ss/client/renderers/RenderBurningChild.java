package palaster97.ss.client.renderers;

import palaster97.ss.entities.EntityBurningChild;
import palaster97.ss.libs.LibResource;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBurningChild extends RenderBiped {

	public RenderBurningChild(RenderManager p_i46168_1_, ModelBiped p_i46168_2_, float p_i46168_3_) {
		super(p_i46168_1_, p_i46168_2_, p_i46168_3_);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		EntityBurningChild bc = (EntityBurningChild) entity;
		if(bc.isActive())
			return LibResource.burningChild_0;
		else
			return LibResource.burningChild_1;
	}
}
