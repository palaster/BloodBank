package palaster97.ss.core.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.blocks.BlockWorldManipulator;
import palaster97.ss.blocks.tile.TileEntityWorldManipulator;
import palaster97.ss.core.helpers.SSItemStackHelper;
import palaster97.ss.items.ItemModStaff;

@SideOnly(Side.CLIENT)
public class SSHUDHandler extends Gui {

	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		if(Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().inGameHasFocus) {
			if(event.type == ElementType.HOTBAR) {
				MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
				if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
					BlockPos pos = mop.getBlockPos();
					IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(pos);
					if(blockState != null && blockState.getBlock() instanceof BlockWorldManipulator) {
						TileEntityWorldManipulator wsm = (TileEntityWorldManipulator) Minecraft.getMinecraft().theWorld.getTileEntity(pos);
						if(wsm != null) {
							ItemStack stack = SSItemStackHelper.getItemStackFromInventory(DimensionManager.getWorld(Minecraft.getMinecraft().theWorld.provider.getDimensionId()), pos, 0);
							if(stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean("IsSet")) {
								int[] temp = stack.getTagCompound().getIntArray("WorldPos");
								BlockPos pos1 = new BlockPos(temp[0], temp[1], temp[2]);
								if(Minecraft.getMinecraft().theWorld.provider.getDimensionId() == stack.getTagCompound().getInteger("DimID")) {
									IBlockState blockState1 = DimensionManager.getWorld(Minecraft.getMinecraft().theWorld.provider.getDimensionId()).getBlockState(pos1);
									if(blockState1 != null)
										Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(blockState1.getBlock()), 5, 5);
								} else {
									World world = DimensionManager.getWorld(stack.getTagCompound().getInteger("DimID"));
									if(world != null)
										if(world.getBlockState(pos1) != null)
											Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(world.getBlockState(pos1).getBlock()), 5, 5);
								}
							}
						}
					}
				}
			} else if(event.type == ElementType.TEXT)
				if(Minecraft.getMinecraft().fontRendererObj != null && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemModStaff)
					if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().hasTagCompound()) {
						ItemStack staff = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
						String power = StatCollector.translateToLocal(((ItemModStaff) staff.getItem()).powers[ItemModStaff.getActivePower(staff)]);
						Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("ss.staff.active") + ": " + power, 2, 2, 0);
					}
		}
	}
}
