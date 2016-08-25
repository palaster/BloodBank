package palaster.bb.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelItztliTablet extends ModelBase {
    
    public ModelRenderer base;
    public ModelRenderer top_05;
    public ModelRenderer top_0;
    public ModelRenderer bottom_0;
    public ModelRenderer bottom_1;

    public ModelItztliTablet() {
        textureWidth = 64;
        textureHeight = 32;
        top_05 = new ModelRenderer(this, 46, 0);
        top_05.setRotationPoint(-3.0F, 3.0F, -1.0F);
        top_05.addBox(0.0F, 0.0F, 0.0F, 7, 1, 2, 0.0F);
        top_0 = new ModelRenderer(this, 50, 0);
        top_0.setRotationPoint(-2.0F, 2.0F, -1.0F);
        top_0.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
        bottom_0 = new ModelRenderer(this, 46, 0);
        bottom_0.setRotationPoint(-3.0F, 18.0F, -1.0F);
        bottom_0.addBox(0.0F, 0.0F, 0.0F, 7, 1, 2, 0.0F);
        base = new ModelRenderer(this, 0, 0);
        base.setRotationPoint(-4.0F, 4.0F, -1.0F);
        base.addBox(0.0F, 0.0F, 0.0F, 9, 14, 2, 0.0F);
        bottom_1 = new ModelRenderer(this, 50, 0);
        bottom_1.setRotationPoint(-2.0F, 19.0F, -1.0F);
        bottom_1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        top_05.render(f5);
        top_0.render(f5);
        bottom_0.render(f5);
        base.render(f5);
        bottom_1.render(f5);
    }
}
