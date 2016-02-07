package palaster.bb.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster.bb.BloodBank;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityModInventory;
import palaster.bb.blocks.tile.TileEntityPlayerManipulator;
import palaster.bb.client.gui.*;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.entities.BBEntities;
import palaster.bb.inventories.*;
import palaster.bb.items.BBItems;
import palaster.bb.network.PacketHandler;
import palaster.bb.potions.BBPotion;
import palaster.bb.recipes.BBRecipes;

public class CommonProxy implements IGuiHandler {

	public Potion death;
	
	public void preInit() {
		CreativeTabBB.init();
		PacketHandler.registerPackets();
		BBBlocks.init();
		BBBlocks.registerTileEntities();
		BBEntities.init();
		BBItems.init();
	}
	
	public void init() {
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(BBItems.hephaestusHammer), 1, 1, 7));
		MinecraftForge.EVENT_BUS.register(new BBEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(BloodBank.instance, this);
		death = new BBPotion(new ResourceLocation("death"), true, 0x000000);
	}
	
	public void postInit() { BBRecipes.init(); }
	
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }
	
	public IThreadListener getThreadFromContext(MessageContext ctx) { return ctx.getServerHandler().playerEntity.getServerForPlayer(); }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 0: return new ContainerSoulCompressor(player.inventory, world, new BlockPos(x, y, z));
			case 1: {
				if(te != null && te instanceof TileEntityModInventory)
					if(((TileEntityModInventory) te).getName().equals("container.voidAnchor"))
						return new ContainerVoidAnchor(player.inventory, (TileEntityModInventory) te);
			}
			case 2: {
				if(te != null && te instanceof TileEntityPlayerManipulator)
					return new ContainerPlayerSoulManipulator(player.inventory, (TileEntityPlayerManipulator) te);
			}
			case 4: {
				if(te != null && te instanceof TileEntityPlayerManipulator)
					return new ContainerPlayerSoulManipulatorInventory(player.inventory, (TileEntityPlayerManipulator) te);
			}
			case 5: {
				if(te != null && te instanceof TileEntityPlayerManipulator)
					return new ContainerPlayerSoulManipulatorPotion(player.inventory, (TileEntityPlayerManipulator) te);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 0: return new GuiSoulCompressor(player.inventory, world, new BlockPos(x, y, z));
			case 1: {
				if(te != null && te instanceof TileEntityModInventory)
					if(((TileEntityModInventory) te).getName().equals("container.voidAnchor"))
						return new GuiVoidAnchor(player.inventory, (TileEntityModInventory) te);
			}
			case 2: {
				if(te != null && te instanceof TileEntityPlayerManipulator)
					return new GuiPlayerManipulator(player.inventory, (TileEntityPlayerManipulator) te);
			}
			case 4: {
				if(te != null && te instanceof TileEntityPlayerManipulator)
					return new GuiPlayerManipulatorInventory(player.inventory, (TileEntityPlayerManipulator) te);
			}
			case 5: {
				if(te != null && te instanceof TileEntityPlayerManipulator)
					return new GuiPlayerManipulatorPotion(player.inventory, (TileEntityPlayerManipulator) te);
			}
		}
		return null;
	}
}
