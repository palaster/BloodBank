package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster.bb.blocks.tile.TileEntityCommunityTool;
import palaster.bb.blocks.tile.TileEntityModInventory;
import palaster.bb.blocks.tile.TileEntityVoid;

public class BBBlocks {
	
	public static Block voidAnchor,
	communityTool;

	public static Block touchVoid;
	
	public static void init() {
		voidAnchor = new BlockVoidAnchor(Material.rock);
		communityTool = new BlockCommunityTool(Material.rock);

		touchVoid = new BlockVoid(Material.barrier);

		registerTileEntities();
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityModInventory.class, "tileEntityModInventory");
		registerTileEntity(TileEntityCommunityTool.class, "communityTool");
		
		registerTileEntity(TileEntityVoid.class, "void");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}