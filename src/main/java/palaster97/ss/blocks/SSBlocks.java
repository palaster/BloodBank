package palaster97.ss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster97.ss.blocks.tile.TileEntityConjuringTablet;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.blocks.tile.TileEntityRitual;
import palaster97.ss.blocks.tile.TileEntityWorldSoulManipulator;

public class SSBlocks {
	
	public static Block soulCompressor;
	public static Block ritual;
	public static Block playerSoulManipulator;
	public static Block worldSoulManipulator;
	public static Block conjuringTablet;
	
	public static void init() {
		soulCompressor = new BlockSoulCompressor(Material.iron);
		ritual = new BlockRitual(Material.rock);
		playerSoulManipulator = new BlockPlayerSoulManipulator(Material.wood);
		worldSoulManipulator = new BlockWorldSoulManipulator(Material.rock);
		conjuringTablet = new BlockConjuringTablet(Material.rock);
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityRitual.class, "ritual");
		registerTileEntity(TileEntityPlayerSoulManipulator.class, "playerSoulManipulator");
		registerTileEntity(TileEntityWorldSoulManipulator.class, "worldSoulManipulator");
		registerTileEntity(TileEntityConjuringTablet.class, "conjuringTablet");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}