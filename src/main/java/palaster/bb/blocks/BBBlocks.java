package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster.bb.blocks.tile.TileEntityModInventory;
import palaster.bb.blocks.tile.TileEntityPlayerManipulator;
import palaster.bb.blocks.tile.TileEntityVoid;
import palaster.bb.blocks.tile.TileEntityWorldManipulator;

public class BBBlocks {
	
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
		registerTileEntity(TileEntityModInventory.class, "tileEntityModInventory");
		registerTileEntity(TileEntityPlayerManipulator.class, "playerManipulator");
		registerTileEntity(TileEntityWorldManipulator.class, "worldManipulator");
		
		registerTileEntity(TileEntityVoid.class, "void");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}