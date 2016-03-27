package palaster.bb.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster.bb.BloodBank;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityModInventory;
import palaster.bb.capabilities.entities.BloodBankCapability;
import palaster.bb.client.gui.GuiLetter;
import palaster.bb.client.gui.GuiVoidAnchor;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.entities.BBEntities;
import palaster.bb.inventories.ContainerLetter;
import palaster.bb.inventories.ContainerVoidAnchor;
import palaster.bb.inventories.InventoryModLetter;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemLetter;
import palaster.bb.network.PacketHandler;
import palaster.bb.potions.BBPotion;
import palaster.bb.recipes.BBRecipes;

public class CommonProxy implements IGuiHandler {

	public DamageSource bbBlood = (new DamageSource("bbBlood")).setDamageBypassesArmor().setMagicDamage();
	
	public void preInit() {
		CreativeTabBB.init();
		PacketHandler.registerPackets();
		BBBlocks.init();
		BBBlocks.registerTileEntities();
		BBEntities.init();
		BBItems.init();
		CapabilityManager.INSTANCE.register(BloodBankCapability.IBloodBank.class, new BloodBankCapability.Storage(), BloodBankCapability.DefaultImpl.class);
		MinecraftForge.EVENT_BUS.register(new BBEventHandler());
	}
	
	public void init() {
		// Broken till new loot system ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(BBItems.hephaestusHammer, 0, 1, 1, 7));
		NetworkRegistry.INSTANCE.registerGuiHandler(BloodBank.instance, this);
		Potion.potionRegistry.putObject(new ResourceLocation("death"), new BBPotion(true, 0x000000).setPotionName("effect.death"));
	}
	
	public void postInit() { BBRecipes.init(); }
	
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }
	
	public IThreadListener getThreadFromContext(MessageContext ctx) { return ctx.getServerHandler().playerEntity.getServerForPlayer(); }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 1: {
				if(te != null && te instanceof TileEntityModInventory)
					if(((TileEntityModInventory) te).getName().equals("container.voidAnchor"))
						return new ContainerVoidAnchor(player.inventory, (TileEntityModInventory) te);
			}
			case 3: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemLetter)
					return new ContainerLetter(player.inventory, new InventoryModLetter(player.getHeldItem(EnumHand.MAIN_HAND)));
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
			}
			case 3: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemLetter)
					return new GuiLetter(player.inventory, new InventoryModLetter(player.getHeldItem(EnumHand.MAIN_HAND)));
			}
		}
		return null;
	}
}
