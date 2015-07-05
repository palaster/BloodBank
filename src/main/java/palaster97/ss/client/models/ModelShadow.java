package palaster97.ss.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelShadow extends ModelBase {
	
	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer lArm;
	ModelRenderer rArm;
	ModelRenderer lArm2;
	ModelRenderer rArm2;
	
	public ModelShadow() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(0F, 0F, 0F, 4, 4, 4);
		head.setRotationPoint(-2F, 12F, -2F);
		setRotation(head, 0F, 0F, 0F);
		body = new ModelRenderer(this, 0, 8);
		body.addBox(0F, 0F, 0F, 4, 4, 2);
		body.setRotationPoint(-2F, 16F, -1F);
		setRotation(body, 0F, 0F, 0F);
		lArm = new ModelRenderer(this, 16, 0);
		lArm.addBox(0F, 0F, 0F, 1, 3, 1);
		lArm.setRotationPoint(-3F, 17F, -1F);
		setRotation(lArm, -0.8726646F, 0F, 0F);
		rArm = new ModelRenderer(this, 16, 0);
		rArm.addBox(0F, 0F, 0F, 1, 3, 1);
		rArm.setRotationPoint(2F, 17F, -1F);
		setRotation(rArm, -0.8726646F, 0F, 0F);
		lArm2 = new ModelRenderer(this, 16, 0);
		lArm2.addBox(0F, 0F, 0F, 1, 3, 1);
		lArm2.setRotationPoint(-3F, 17F, -5F);
		setRotation(lArm2, 0.6981317F, 0F, 0F);
		rArm2 = new ModelRenderer(this, 16, 0);
		rArm2.addBox(0F, 0F, 0F, 1, 3, 1);
		rArm2.setRotationPoint(2F, 17F, -5F);
		setRotation(rArm2, 0.6981317F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		head.render(f5);
		body.render(f5);
		lArm.render(f5);
		rArm.render(f5);
		lArm2.render(f5);
		rArm2.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}