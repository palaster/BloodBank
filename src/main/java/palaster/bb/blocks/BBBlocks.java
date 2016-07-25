package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster.bb.blocks.tile.TileEntityCommunityTool;
import palaster.bb.blocks.tile.TileEntityTNTAbsorber;
import palaster.bb.blocks.tile.TileEntityVoid;
import palaster.bb.blocks.tile.TileEntityVoidAnchor;
import palaster.bb.libs.LibMod;

public class BBBlocks {
	
	public static Block voidAnchor,
	communityTool,
	bonfire,
	tntAbsorber;

	public static Block touchVoid;
	
	public static void init() {
		voidAnchor = new BlockVoidAnchor(Material.ROCK);
		communityTool = new BlockCommunityTool(Material.ROCK);
		bonfire = new BlockBonfire(Material.WOOD);
		tntAbsorber = new BlockTNTAbsorber(Material.ROCK);

		touchVoid = new BlockVoid(Material.BARRIER);

		registerTileEntities();
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityVoidAnchor.class, "voidAnchor");
		registerTileEntity(TileEntityCommunityTool.class, "communityTool");
		registerTileEntity(TileEntityTNTAbsorber.class, "tntAbsorber");
		
		registerTileEntity(TileEntityVoid.class, "void");
	}
	
	public static void registerCustomModelResourceLocation() {
		for(Block block : Block.REGISTRY)
			if(block.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.modid))
				BlockMod.setCustomModelResourceLocation(block);
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}