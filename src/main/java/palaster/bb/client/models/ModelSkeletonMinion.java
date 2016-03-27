package palaster.bb.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntitySkeletonMinion;

@SideOnly(Side.CLIENT)
public class ModelSkeletonMinion extends ModelBiped {

	public ModelSkeletonMinion() { this(0.0F, false); }

    public ModelSkeletonMinion(float p_i46303_1_, boolean p_i46303_2_) {
        super(p_i46303_1_, 0.0F, 64, 32);
        if(!p_i46303_2_) {
            bipedRightArm = new ModelRenderer(this, 40, 16);
            bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
            bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
            bipedLeftArm = new ModelRenderer(this, 40, 16);
            bipedLeftArm.mirror = true;
            bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
            bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            bipedRightLeg = new ModelRenderer(this, 0, 16);
            bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
            bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
            bipedLeftLeg = new ModelRenderer(this, 0, 16);
            bipedLeftLeg.mirror = true;
            bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
            bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        }
    }

    @Override
    public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
        rightArmPose = ModelBiped.ArmPose.EMPTY;
        leftArmPose = ModelBiped.ArmPose.EMPTY;
        ItemStack itemstack = p_78086_1_.getHeldItem(EnumHand.MAIN_HAND);

        if(itemstack != null && itemstack.getItem() == Items.bow && ((EntitySkeleton)p_78086_1_).func_184725_db())
            if(p_78086_1_.getPrimaryHand() == EnumHandSide.RIGHT)
                rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            else
                leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
        super.setLivingAnimations(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) { super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_); }
}
