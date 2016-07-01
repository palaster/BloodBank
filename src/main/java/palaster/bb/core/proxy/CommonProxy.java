package palaster.bb.core.proxy;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityFactory;
import palaster.bb.api.capabilities.entities.BloodBankCapabilityStorage;
import palaster.bb.api.capabilities.entities.IBloodBank;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapabilityFactory;
import palaster.bb.api.capabilities.entities.UndeadCapabilityStorage;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityModInventory;
import palaster.bb.client.gui.GuiLetter;
import palaster.bb.client.gui.GuiUndeadMonitor;
import palaster.bb.client.gui.GuiVoidAnchor;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.entities.BBEntities;
import palaster.bb.entities.EntityItztiliTablet;
import palaster.bb.entities.effects.BBPotions;
import palaster.bb.inventories.ContainerLetter;
import palaster.bb.inventories.ContainerUndeadMonitor;
import palaster.bb.inventories.ContainerVoidAnchor;
import palaster.bb.inventories.InventoryModLetter;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemLetter;
import palaster.bb.items.ItemUndeadMonitor;
import palaster.bb.network.PacketHandler;
import palaster.bb.recipes.BBRecipes;

public class CommonProxy implements IGuiHandler {
	
	public DamageSource bbBlood = (new DamageSource("bbBlood")).setDamageBypassesArmor().setMagicDamage();
	public VillagerProfession villageSpellSeller;
	
	public void preInit() {
		CreativeTabBB.init();
		PacketHandler.registerPackets();
		BBBlocks.init();
		BBEntities.init();
		BBItems.init();
		BBPotions.init();
		CapabilityManager.INSTANCE.register(IBloodBank.class, new BloodBankCapabilityStorage(), new BloodBankCapabilityFactory());
		CapabilityManager.INSTANCE.register(IUndead.class, new UndeadCapabilityStorage(), new UndeadCapabilityFactory());
		MinecraftForge.EVENT_BUS.register(new BBEventHandler());
	}
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(BloodBank.instance, this);
		villageSpellSeller = new VillagerRegistry.VillagerProfession("bb:villageSpellSeller", "bb:textures/models/spellSeller.png", "minecraft:textures/entity/zombie_villager/zombie_villager.png");
		VillagerRegistry.instance().register(villageSpellSeller);
		VillagerRegistry.VillagerCareer careerSpell = new VillagerCareer(villageSpellSeller, "spell");
		careerSpell.addTrade(1, new EntityVillager.ListItemForEmeralds(BBItems.sacredFlame, new PriceInfo(1, 4)));
		BBApi.addBossClassToToken(EntityItztiliTablet.class);
	}
	
	public void postInit() { BBRecipes.init(); }
	
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }
	
	public IThreadListener getThreadFromContext(MessageContext ctx) { return ctx.getServerHandler().playerEntity.getServerWorld(); }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 1: {
				if(te != null && te instanceof TileEntityModInventory)
					if(((TileEntityModInventory) te).getName().equals("container.voidAnchor"))
						return new ContainerVoidAnchor(player.inventory, (TileEntityModInventory) te);
				break;
			}
			case 2: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemUndeadMonitor)
					return new ContainerUndeadMonitor(player);
				break;
			}
			case 3: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemLetter)
					return new ContainerLetter(player.inventory, new InventoryModLetter(player.getHeldItem(EnumHand.MAIN_HAND)));
				break;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 1: {
				if(te != null && te instanceof TileEntityModInventory)
					if(((TileEntityModInventory) te).getName().equals("container.voidAnchor"))
						return new GuiVoidAnchor(player.inventory, (TileEntityModInventory) te);
				break;
			}
			case 2: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemUndeadMonitor)
					return new GuiUndeadMonitor(player);
				break;
			}
			case 3: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemLetter)
					return new GuiLetter(player.inventory, new InventoryModLetter(player.getHeldItem(EnumHand.MAIN_HAND)));
				break;
			}
		}
		return null;
	}
}
