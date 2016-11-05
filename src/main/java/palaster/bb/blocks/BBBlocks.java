package palaster.bb.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.blocks.tile.TileEntityBloodTicker;
import palaster.bb.blocks.tile.TileEntityDesalinator;
import palaster.bb.blocks.tile.TileEntityTNTAbsorber;
import palaster.bb.blocks.tile.TileEntityVoidAnchor;
import palaster.bb.blocks.tile.TileEntityVoidTrap;
import palaster.bb.libs.LibMod;
import palaster.libpal.blocks.BlockMod;

public class BBBlocks {
	
	public static Block voidAnchor,
	tntAbsorber,
	bloodTicker,
	voidTrap,
	desalinator,
	slotMachine;
	
	public static void init() {
		voidAnchor = new BlockVoidAnchor(createResourceLocation("voidAnchor"), Material.ROCK);
		tntAbsorber = new BlockTNTAbsorber(createResourceLocation("tntAbsorber"), Material.ROCK);
		bloodTicker = new BlockBloodTicker(createResourceLocation("bloodTicker"), Material.GROUND);
		voidTrap = new BlockVoidTrap(createResourceLocation("voidTrap"), Material.BARRIER);
		desalinator = new BlockDesalinator(createResourceLocation("desalinator"), Material.ROCK);
		slotMachine = new BlockSlotMachine(createResourceLocation("slotMachine"), Material.ROCK);

		registerTileEntities();
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityVoidAnchor.class, "voidAnchor");
		registerTileEntity(TileEntityTNTAbsorber.class, "tntAbsorber");
		registerTileEntity(TileEntityBloodTicker.class, "bloodTicker");
		registerTileEntity(TileEntityVoidTrap.class, "voidTrap");
		registerTileEntity(TileEntityDesalinator.class, "desalinator");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tile, String name) { GameRegistry.registerTileEntity(tile, name); }
	
	private static final ResourceLocation createResourceLocation(String unlocalizedName) { return new ResourceLocation(LibMod.MODID, unlocalizedName); }

	@SideOnly(Side.CLIENT)
	public static void registerCustomModelResourceLocation() {
		for(Block block : Block.REGISTRY)
			if(block instanceof BlockMod && block.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.MODID))
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}