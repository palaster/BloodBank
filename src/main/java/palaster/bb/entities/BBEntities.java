package palaster.bb.entities;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import palaster.bb.client.renderers.RenderFactoryBeaver;
import palaster.bb.client.renderers.RenderFactoryDemonicBankTeller;
import palaster.bb.BloodBank;
import palaster.bb.client.renderers.RenderFactorySkeletonMinion;
import palaster.bb.client.renderers.RenderFactoryYinYang;

public class BBEntities {

	public static void init() {
		EntityRegistry.registerModEntity(EntitySkeletonMinion.class, "skeletonMinion", 0, BloodBank.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityYinYang.class, "yinYang", 1, BloodBank.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityBeaver.class, "beaver", 2, BloodBank.instance, 80, 3, true, 0x8A0707, 0x663300);
		EntityRegistry.registerModEntity(EntityDemonicBankTeller.class, "demonicBankTeller", 3, BloodBank.instance, 80, 3, true, 0x8A0707, 0x663300);
	}

	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonMinion.class, new RenderFactorySkeletonMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityYinYang.class, new RenderFactoryYinYang());
		RenderingRegistry.registerEntityRenderingHandler(EntityBeaver.class, new RenderFactoryBeaver());
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonicBankTeller.class, new RenderFactoryDemonicBankTeller());
	}
}
