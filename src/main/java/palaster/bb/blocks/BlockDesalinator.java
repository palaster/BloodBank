package palaster.bb.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.blocks.tile.TileEntityDesalinator;

public class BlockDesalinator extends BlockModContainer {

	public BlockDesalinator(Material p_i45394_1_) {
		super(p_i45394_1_);
		setUnlocalizedName("desalinator");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) { tooltip.add(I18n.format("player.salt")); }
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) { return new TileEntityDesalinator(); }
}
