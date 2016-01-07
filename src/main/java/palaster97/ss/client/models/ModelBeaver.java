package palaster97.ss.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBeaver extends ModelBase {

    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rLeg;
    public ModelRenderer lLeg;
    public ModelRenderer rArm;
    public ModelRenderer lArm;
    public ModelRenderer tail;
    public ModelRenderer teeth;

    public ModelBeaver() {
        textureWidth = 64;
        textureHeight = 32;
        rLeg = new ModelRenderer(this, 26, 23);
        rLeg.setRotationPoint(-4.5F, 20.0F, -3.0F);
        rLeg.addBox(0.0F, 0.0F, 0.0F, 4, 4, 5, 0.0F);
        rArm = new ModelRenderer(this, 48, 20);
        rArm.setRotationPoint(-7.5F, 12.0F, -3.0F);
        rArm.addBox(0.0F, 0.0F, 0.0F, 3, 7, 5, 0.0F);
        lArm = new ModelRenderer(this, 48, 20);
        lArm.setRotationPoint(3.5F, 12.0F, -3.0F);
        lArm.addBox(0.0F, 0.0F, 0.0F, 3, 7, 5, 0.0F);
        body = new ModelRenderer(this, 0, 19);
        body.setRotationPoint(-4.5F, 12.0F, -3.0F);
        body.addBox(0.0F, 0.0F, 0.0F, 8, 8, 5, 0.0F);
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(-0.5F, 12.0F, -0.5F);
        head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        lLeg = new ModelRenderer(this, 26, 23);
        lLeg.setRotationPoint(-0.5F, 20.0F, -3.0F);
        lLeg.addBox(0.0F, 0.0F, 0.0F, 4, 4, 5, 0.0F);
        tail = new ModelRenderer(this, 32, 0);
        tail.setRotationPoint(-3.5F, 17.0F, 2.0F);
        tail.addBox(0.0F, 0.0F, 0.0F, 6, 4, 10, 0.0F);
        setRotateAngle(tail, 0.5585053606381855F, 0.0F, 0.0F);
        teeth = new ModelRenderer(this, 32, 20);
        teeth.setRotationPoint(-0.5F, 12.0F, -0.5F);
        teeth.addBox(-1.0F, -3.0F, -5.0F, 2, 2, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        rLeg.render(f5);
        lArm.render(f5);
        lLeg.render(f5);
        teeth.render(f5);
        head.render(f5);
        rArm.render(f5);
        body.render(f5);
        tail.render(f5);
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn) {
        head.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        teeth.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        teeth.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        rArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 2.0F * p_78087_2_ * 0.5F;
        lArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
        rArm.rotateAngleZ = 0.0F;
        lArm.rotateAngleZ = 0.0F;
        rLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
        lLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
        rLeg.rotateAngleY = 0.0F;
        lLeg.rotateAngleY = 0.0F;
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
