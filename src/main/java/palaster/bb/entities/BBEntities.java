package palaster.bb.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import palaster.bb.BloodBank;
import palaster.bb.client.renderers.RenderDemonicBankTeller;
import palaster.bb.client.renderers.RenderItztiliTablet;
import palaster.bb.client.renderers.RenderSkeletonMinion;
import palaster.bb.client.renderers.RenderThrowable;
import palaster.bb.items.BBItems;

public class BBEntities {

	public static void init() {
		EntityRegistry.registerModEntity(EntitySkeletonMinion.class, "skeletonMinion", 0, BloodBank.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityYinYang.class, "yinYang", 1, BloodBank.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityDemonicBankTeller.class, "demonicBankTeller", 2, BloodBank.instance, 80, 3, true, 0x8A0707, 0x663300);
		EntityRegistry.registerModEntity(EntityItztiliTablet.class, "itztiliTablet", 3, BloodBank.instance, 80, 3, true, 0x8A0707, 0x00FF00);
		EntityRegistry.registerModEntity(EntityTalisman.class, "talisman", 4, BloodBank.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityPectusRapientem.class, "pectusRapientem", 5, BloodBank.instance, 80, 3, true, 0x8A0707, 0x000000);
	}

	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonMinion.class, new IRenderFactory<EntitySkeletonMinion>() {
			@Override
			public Render<? super EntitySkeletonMinion> createRenderFor(RenderManager manager) { return new RenderSkeletonMinion(manager); }
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityYinYang.class, new IRenderFactory<EntityYinYang>() {
			@Override
			public Render<? super EntityYinYang> createRenderFor(RenderManager manager) { return new RenderThrowable<EntityYinYang>(manager, BBItems.yinYang, Minecraft.getMinecraft().getRenderItem()); }
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonicBankTeller.class, new IRenderFactory<EntityDemonicBankTeller>() {
			@Override
			public Render<? super EntityDemonicBankTeller> createRenderFor(RenderManager manager) { return new RenderDemonicBankTeller(manager); }
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityItztiliTablet.class, new IRenderFactory<EntityItztiliTablet>() {
			@Override
			public Render<? super EntityItztiliTablet> createRenderFor(RenderManager manager) { return new RenderItztiliTablet(manager); }
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityTalisman.class, new IRenderFactory<EntityTalisman>() {
			@Override
			public Render<? super EntityTalisman> createRenderFor(RenderManager manager) { return new RenderThrowable<EntityTalisman>(manager, BBItems.talisman, Minecraft.getMinecraft().getRenderItem()); }
		});
	}
}