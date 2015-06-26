package palaster97.ss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster97.ss.blocks.tile.TileEntityConjuringTablet;
import palaster97.ss.blocks.tile.TileEntityPlayerSoulManipulator;
import palaster97.ss.blocks.tile.TileEntityVoid;
import palaster97.ss.blocks.tile.TileEntityVoidAnchor;
import palaster97.ss.blocks.tile.TileEntityWorldSoulManipulator;

public class SSBlocks {
	
	public static Block soulCompressor,
	playerSoulManipulator,
	worldSoulManipulator,
	conjuringTablet,
	voidAnchor;
	
	public static Block touchVoid;
	
	public static void init() {
		soulCompressor = new BlockSoulCompressor(Material.wood);
		playerSoulManipulator = new BlockPlayerSoulManipulator(Material.wood);
		worldSoulManipulator = new BlockWorldSoulManipulator(Material.rock);
		conjuringTablet = new BlockConjuringTablet(Material.rock);
		voidAnchor = new BlockVoidAnchor(Material.rock);
		
		touchVoid = new BlockVoid(Material.barrier);
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityPlayerSoulManipulator.class, "playerSoulManipulator");
		registerTileEntity(TileEntityWorldSoulManipulator.class, "worldSoulManipulator");
		registerTileEntity(TileEntityConjuringTablet.class, "conjuringTablet");
		registerTileEntity(TileEntityVoidAnchor.class, "voidAnchor");
		
		registerTileEntity(TileEntityVoid.class, "void");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}