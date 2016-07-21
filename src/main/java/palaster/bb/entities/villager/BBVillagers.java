package palaster.bb.entities.villager;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import palaster.bb.items.BBItems;

public class BBVillagers {
	
	public static VillagerProfession villageSpellSeller;

	public static void init() {
		villageSpellSeller = new VillagerRegistry.VillagerProfession("bb:villageSpellSeller", "bb:textures/models/spellSeller.png", "minecraft:textures/entity/zombie_villager/zombie_villager.png");
		VillagerRegistry.instance().register(villageSpellSeller);
		VillagerRegistry.VillagerCareer careerSpell = new VillagerCareer(villageSpellSeller, "spell");
		careerSpell.addTrade(1, new EntityVillager.ListItemForEmeralds(BBItems.sacredFlame, new PriceInfo(1, 4)));
		careerSpell.addTrade(1, new EntityVillager.ListItemForEmeralds(BBItems.carthusFlameArc, new PriceInfo(1, 4)));
	}
}
