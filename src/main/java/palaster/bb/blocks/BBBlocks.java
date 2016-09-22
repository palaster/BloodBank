package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster.bb.blocks.tile.TileEntityBloodTicker;
import palaster.bb.blocks.tile.TileEntityCommunityTool;
import palaster.bb.blocks.tile.TileEntityDesalinator;
import palaster.bb.blocks.tile.TileEntityTNTAbsorber;
import palaster.bb.blocks.tile.TileEntityVoidAnchor;
import palaster.bb.blocks.tile.TileEntityVoidTrap;
import palaster.bb.libs.LibMod;

public class BBBlocks {
	
	public static Block voidAnchor,
	communityTool,
	bonfire,
	tntAbsorber,
	bloodTicker,
	voidTrap,
	desalinator,
	slotMachine;
	
	public static void init() {
		voidAnchor = new BlockVoidAnchor(Material.ROCK);
		communityTool = new BlockCommunityTool(Material.ROCK);
		bonfire = new BlockBonfire(Material.WOOD);
		tntAbsorber = new BlockTNTAbsorber(Material.ROCK);
		bloodTicker = new BlockBloodTicker(Material.GROUND);
		voidTrap = new BlockVoidTrap(Material.BARRIER);
		desalinator = new BlockDesalinator(Material.ROCK);
		slotMachine = new BlockSlotMachine(Material.ROCK);

		registerTileEntities();
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityVoidAnchor.class, "voidAnchor");
		registerTileEntity(TileEntityCommunityTool.class, "communityTool");
		registerTileEntity(TileEntityTNTAbsorber.class, "tntAbsorber");
		registerTileEntity(TileEntityBloodTicker.class, "bloodTicker");
		registerTileEntity(TileEntityVoidTrap.class, "voidTrap");
		registerTileEntity(TileEntityDesalinator.class, "desalinator");
	}
	
	public static void registerCustomModelResourceLocation() {
		for(Block block : Block.REGISTRY)
			if(block.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.MODID))
				BlockMod.setCustomModelResourceLocation(block);
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}