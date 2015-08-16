package palaster97.ss.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.blocks.SSBlocks;
import palaster97.ss.blocks.tile.TileEntityPlayerManipulator;
import palaster97.ss.blocks.tile.TileEntityVoidAnchor;
import palaster97.ss.client.gui.GuiPlayerManipulator;
import palaster97.ss.client.gui.GuiPlayerManipulatorInventory;
import palaster97.ss.client.gui.GuiPlayerManipulatorPotion;
import palaster97.ss.client.gui.GuiSoulCompressor;
import palaster97.ss.client.gui.GuiVoidAnchor;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.core.handlers.SSEventHandler;
import palaster97.ss.core.handlers.SSFMLEventHandler;
import palaster97.ss.entities.SSEntities;
import palaster97.ss.inventories.ContainerPlayerSoulManipulator;
import palaster97.ss.inventories.ContainerPlayerSoulManipulatorInventory;
import palaster97.ss.inventories.ContainerPlayerSoulManipulatorPotion;
import palaster97.ss.inventories.ContainerSoulCompressor;
import palaster97.ss.inventories.ContainerVoidAnchor;
import palaster97.ss.items.SSItems;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.recipes.SSRecipes;

public class CommonProxy implements IGuiHandler {
	
	public void preInit() {
		CreativeTabSS.init();
		PacketHandler.registerPackets();
	}
	
	public void init() {
		SSBlocks.init();
		SSBlocks.registerTileEntities();
		SSItems.init();
		SSEntities.init();
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(SSItems.hephaestusHammer), 1, 1, 7));
		FMLCommonHandler.instance().bus().register(new SSFMLEventHandler());
		MinecraftForge.EVENT_BUS.register(new SSEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(ScreamingSouls.instance, this);
	}
	
	public void postInit() { SSRecipes.init(); }
	
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }
	
	public IThreadListener getThreadFromContext(MessageContext ctx) { return ctx.getServerHandler().playerEntity.getServerForPlayer(); }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 0: return new ContainerSoulCompressor(player.inventory, world, new BlockPos(x, y, z));
			case 1: {
				if(te != null && te instanceof TileEntityVoidAnchor)
					return new ContainerVoidAnchor(player.inventory, (TileEntityVoidAnchor) te);
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
				if(te != null && te instanceof TileEntityVoidAnchor)
					return new GuiVoidAnchor(player.inventory, (TileEntityVoidAnchor) te);
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
