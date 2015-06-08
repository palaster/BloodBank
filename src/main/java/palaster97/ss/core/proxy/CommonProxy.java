package palaster97.ss.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.blocks.SSBlocks;
import palaster97.ss.blocks.tile.TileEntityConjuringTablet;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.client.gui.GuiConjuringTablet;
import palaster97.ss.client.gui.GuiPlayerSoulManipulator;
import palaster97.ss.client.gui.GuiPlayerSoulManipulatorInventory;
import palaster97.ss.client.gui.GuiPlayerSoulManipulatorPotion;
import palaster97.ss.client.gui.GuiSoulCompressor;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.core.handlers.SSEventHandler;
import palaster97.ss.core.handlers.SSFMLEventHandler;
import palaster97.ss.core.handlers.SSPlayerTickHandler;
import palaster97.ss.entities.SSEntities;
import palaster97.ss.inventories.ContainerConjuringTablet;
import palaster97.ss.inventories.ContainerPlayerSoulManipulator;
import palaster97.ss.inventories.ContainerPlayerSoulManipulatorInventory;
import palaster97.ss.inventories.ContainerPlayerSoulManipulatorPotion;
import palaster97.ss.inventories.ContainerSoulCompressor;
import palaster97.ss.items.SSItems;
import palaster97.ss.network.PacketHandler;
import palaster97.ss.recipes.ConjuringTabletRecipes;
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
		ConjuringTabletRecipes.registerConjuringTablet();
		FMLCommonHandler.instance().bus().register(new SSFMLEventHandler());
		FMLCommonHandler.instance().bus().register(new SSPlayerTickHandler());
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
			case 2: {
				if(te != null && te instanceof TileEntityPlayerSoulManipulator)
					return new ContainerPlayerSoulManipulator(player.inventory, (TileEntityPlayerSoulManipulator) te);
			}
			case 3: {
				if(te != null && te instanceof TileEntityConjuringTablet)
					return new ContainerConjuringTablet(player.inventory, (TileEntityConjuringTablet) te);
			}
			case 4: {
				if(te != null && te instanceof TileEntityPlayerSoulManipulator)
					return new ContainerPlayerSoulManipulatorInventory(player.inventory, (TileEntityPlayerSoulManipulator) te);
			}
			case 5: {
				if(te != null && te instanceof TileEntityPlayerSoulManipulator)
					return new ContainerPlayerSoulManipulatorPotion(player.inventory, (TileEntityPlayerSoulManipulator) te);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 0: return new GuiSoulCompressor(player.inventory, world, new BlockPos(x, y, z));
			case 2: {
				if(te != null && te instanceof TileEntityPlayerSoulManipulator)
					return new GuiPlayerSoulManipulator(player.inventory, (TileEntityPlayerSoulManipulator) te);
			}
			case 3: {
				if(te != null && te instanceof TileEntityConjuringTablet)
					return new GuiConjuringTablet(player.inventory, (TileEntityConjuringTablet) te);
			}
			case 4: {
				if(te != null && te instanceof TileEntityPlayerSoulManipulator)
					return new GuiPlayerSoulManipulatorInventory(player.inventory, (TileEntityPlayerSoulManipulator) te);
			}
			case 5: {
				if(te != null && te instanceof TileEntityPlayerSoulManipulator)
					return new GuiPlayerSoulManipulatorPotion(player.inventory, (TileEntityPlayerSoulManipulator) te);
			}
		}
		return null;
	}
}
