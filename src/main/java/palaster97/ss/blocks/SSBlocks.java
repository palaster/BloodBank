package palaster97.ss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster97.ss.blocks.tile.*;

public class SSBlocks {
	
	public static Block soulCompressor,
	playerManipulator,
	worldManipulator,
	voidAnchor;
	
	public static Block touchVoid;
	
	public static void init() {
		soulCompressor = new BlockSoulCompressor(Material.wood);
		playerManipulator = new BlockPlayerManipulator(Material.wood);
		worldManipulator = new BlockWorldManipulator(Material.rock);
		voidAnchor = new BlockVoidAnchor(Material.rock);
		
		touchVoid = new BlockVoid(Material.barrier);
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityPlayerManipulator.class, "playerManipulator");
		registerTileEntity(TileEntityWorldManipulator.class, "worldManipulator");
		registerTileEntity(TileEntityVoidAnchor.class, "voidAnchor");
		
		registerTileEntity(TileEntityVoid.class, "void");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}