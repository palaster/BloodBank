package palaster97.ss.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import palaster97.ss.blocks.tile.TileEntityRitual;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.rituals.Ritual;
import palaster97.ss.rituals.RitualActive;

public class BlockRitual extends BlockModContainer {

	public BlockRitual(Material p_i45394_1_) {
		super(p_i45394_1_);
		setBlockBounds(0, 0, 0, 1, .625f, 1);
		setUnlocalizedName("ritual");
	}
	
	@Override
	public boolean isFullCube() { return false; }
	
	@Override
	public int getRenderType() { return 3; }
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te != null && te instanceof IInventory) {
				IInventory inv = (IInventory) te;
				ItemStack stack = inv.getStackInSlotOnClosing(0);
				inv.setInventorySlotContents(0, null);
				if(stack != null && stack.getItem() != Item.getItemFromBlock(Blocks.bedrock)) {
					EntityItem drop = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
					worldIn.spawnEntityInWorld(drop);
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te != null && te instanceof TileEntityRitual) {
				TileEntityRitual ritualBlock = (TileEntityRitual) te;
				if(!ritualBlock.getIsActive()) {
					if(ritualBlock.getStackInSlot(0) != null) {
						if(playerIn.isSneaking()) {
							if(ritualBlock.getStackInSlot(0).getItem() != Item.getItemFromBlock(Blocks.bedrock)) {
								EntityItem drop = new EntityItem(worldIn, playerIn.posX, playerIn.posY + playerIn.getEyeHeight() / 2.0F, playerIn.posZ, ritualBlock.getStackInSlot(0));
								worldIn.spawnEntityInWorld(drop);
								ritualBlock.setInventorySlotContents(0, null);
								worldIn.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
								return true;
							}
						} else {
							for(int i = 0; i < Ritual.rituals.length; i++) {
								if(Ritual.rituals[i] != null && Ritual.rituals[i].stack != null) {
									if(Ritual.rituals[i].stack.getItem() == ritualBlock.getStackInSlot(0).getItem()) {
										if(Ritual.rituals[i] instanceof RitualActive) {
											SoulNetworkExtendedPlayer props = SoulNetworkExtendedPlayer.get(playerIn);
											if(props != null) {
												RitualActive act = (RitualActive) Ritual.rituals[i];
												act.ritualPos = pos;
												ritualBlock.setInventorySlotContents(0, null);
												ritualBlock.setRitualID(act.ritualID, playerIn);
												setBlockUnbreakable();
												props.addRitual(act);
												return true;
											}
										} else {
											SoulNetworkExtendedPlayer props = SoulNetworkExtendedPlayer.get(playerIn);
											if(props != null) {
												Ritual.rituals[i].activate(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
												ritualBlock.setInventorySlotContents(0, null);
												return true;
											}
										}
									}
								}
							}
						}
					}
					if(playerIn.getCurrentEquippedItem() != null && ritualBlock.getStackInSlot(0) == null) {
						ItemStack i = playerIn.getCurrentEquippedItem().copy();
						i.stackSize = 1;
						ritualBlock.setInventorySlotContents(0, i);
						playerIn.getCurrentEquippedItem().stackSize -= 1;
						if(playerIn.getCurrentEquippedItem().stackSize == 0)
							playerIn.setCurrentItemOrArmor(0, null);
						playerIn.inventory.markDirty();
						worldIn.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);
						return true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) { return new TileEntityRitual(); }
}
