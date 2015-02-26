package palaster97.ss.core.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.BlockWorldSoulManipulator;
import palaster97.ss.blocks.tile.TileEntityWorldSoulManipulator;
import palaster97.ss.core.helpers.SSItemStackHelper;

@SideOnly(Side.CLIENT)
public class SSHUDHandler extends Gui {
	
	Minecraft mc = Minecraft.getMinecraft();
	RenderItem ri = mc.getRenderItem();

	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		if(event.type == ElementType.HOTBAR && mc.currentScreen == null) {
			MovingObjectPosition mop = mc.objectMouseOver;
			if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
				BlockPos pos = mop.func_178782_a();
				IBlockState blockState = mc.theWorld.getBlockState(pos);
				if(blockState != null && blockState.getBlock() instanceof BlockWorldSoulManipulator) {
					TileEntityWorldSoulManipulator wsm = (TileEntityWorldSoulManipulator) mc.theWorld.getTileEntity(pos);
					if(wsm != null) {
						ItemStack stack = SSItemStackHelper.getItemStackFromInventory(DimensionManager.getWorld(mc.theWorld.provider.getDimensionId()), pos, 0);
						if(stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean("IsSet")) {
							int[] temp = stack.getTagCompound().getIntArray("WorldPos");
							BlockPos pos1 = new BlockPos(temp[0], temp[1], temp[2]);
							if(mc.theWorld.provider.getDimensionId() == stack.getTagCompound().getInteger("DimID")) {
								IBlockState blockState1 = DimensionManager.getWorld(mc.theWorld.provider.getDimensionId()).getBlockState(pos1);
								if(blockState1 != null)
									ri.func_180450_b(new ItemStack(blockState1.getBlock()), 5, 5);
							} else {
								World world = DimensionManager.getWorld(stack.getTagCompound().getInteger("DimID"));
								if(world != null) {
									IBlockState blockState1 = world.getBlockState(pos1);
									if(blockState1 != null)
										ri.func_180450_b(new ItemStack(blockState1.getBlock()), 5, 5);
								}
							}
						}
					}
				}
			}
		}
	}
}
