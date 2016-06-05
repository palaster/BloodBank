package palaster.bb.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import palaster.bb.blocks.tile.TileEntityCommunityTool;
import palaster.bb.core.helpers.BBItemStackHelper;

public class BlockCommunityTool extends BlockModContainer {

    public BlockCommunityTool(Material material) {
        super(material);
        setBlockUnbreakable();
        setUnlocalizedName("communityTool");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if(!e.player.worldObj.isRemote)
			if(e.phase == TickEvent.Phase.START)
				for(int i = 0; i < e.player.inventory.getSizeInventory(); i++)
					if(e.player.inventory.getStackInSlot(i) != null && e.player.inventory.getStackInSlot(i).hasTagCompound())
						if(BBItemStackHelper.getCountDown(e.player.inventory.getStackInSlot(i))) {
							if(e.player.inventory.getStackInSlot(0).getItemDamage() < e.player.inventory.getStackInSlot(0).getMaxDamage())
								e.player.inventory.getStackInSlot(0).damageItem(1, e.player);
							else
								e.player.inventory.setInventorySlotContents(i, null);
						}
	}
    
    @SubscribeEvent
	public void onItemToss(ItemTossEvent e) {
		if(!e.getPlayer().worldObj.isRemote)
			if(e.getEntityItem().getEntityItem().hasTagCompound() && BBItemStackHelper.getCountDown(e.getEntityItem().getEntityItem()))
				e.setCanceled(true);
	}
    
    @SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		if(e.getItemStack() != null && e.getItemStack().hasTagCompound())
			if(BBItemStackHelper.getCountDown(e.getItemStack()))
				e.getToolTip().add(I18n.format("bb.misc.countDown"));
	}

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
            TileEntityCommunityTool ct = (TileEntityCommunityTool) worldIn.getTileEntity(pos);
            if(ct != null) {
                if(ct.getOwner() == null)
                    ct.setOwner(playerIn.getUniqueID());
                else {
                    if(playerIn.isSneaking() && ct.getOwner().equals(playerIn.getUniqueID())) {
                        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                        worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this)));
                        return true;
                    }
                    if(ct.getStackInSlot(0) != null) {
                        if(ct.getOwner().equals(playerIn.getUniqueID())) {
                            if(heldItem == null) {
                                playerIn.setHeldItem(EnumHand.MAIN_HAND, ct.getStackInSlot(0));
                                ct.setInventorySlotContents(0, null);
                                return true;
                            }
                        } else {
                            ItemStack ghostStack = ct.getStackInSlot(0).copy();
                            if(heldItem == null) {
                                playerIn.setHeldItem(hand, BBItemStackHelper.setCountDown(ghostStack, 6000));
                                return true;
                            }
                        }
                    } else {
                        if(ct.getOwner().equals(playerIn.getUniqueID()))
                            if(heldItem != null && heldItem.getMaxStackSize() == 1) {
                                ct.setInventorySlotContents(0, heldItem);
                                playerIn.setHeldItem(EnumHand.MAIN_HAND, null);
                                return true;
                            }
                    }
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) { return new TileEntityCommunityTool(); }
}
