package palaster97.ss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster97.ss.blocks.tile.TileEntityPlayerManipulator;
import palaster97.ss.blocks.tile.TileEntityVoid;
import palaster97.ss.blocks.tile.TileEntityWorldManipulator;
import palaster97.ss.libs.LibMod;

public class SSBlocks {

	public static FluidMod blood;
	
	public static Block soulCompressor,
	playerManipulator,
	worldManipulator,
	voidAnchor,
	blockBlood;
	
	public static Block touchVoid;
	
	public static void init() {
		blood = new FluidMod("blood", new ResourceLocation(LibMod.modid, "blocks/blood_still"), new ResourceLocation(LibMod.modid, "blocks/blood_flow"));
		blood.setDensity(1060).setViscosity(1040);
		FluidRegistry.registerFluid(blood);

		soulCompressor = new BlockSoulCompressor(Material.wood);
		playerManipulator = new BlockPlayerManipulator(Material.wood);
		worldManipulator = new BlockWorldManipulator(Material.rock);
		voidAnchor = new BlockVoidAnchor(Material.rock);
		blockBlood = new BlockModFluidClassic(blood, Material.water);
		blood.setBlock(blockBlood);
		
		touchVoid = new BlockVoid(Material.barrier);
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityPlayerManipulator.class, "playerManipulator");
		registerTileEntity(TileEntityWorldManipulator.class, "worldManipulator");
		
		registerTileEntity(TileEntityVoid.class, "void");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
}