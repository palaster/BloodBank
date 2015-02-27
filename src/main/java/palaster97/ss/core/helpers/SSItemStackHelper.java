package palaster97.ss.core.helpers;

import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;
import palaster97.ss.items.SSItems;

public class SSItemStackHelper {

	public static ItemStack getItemStackFromInventory(WorldServer ws, BlockPos pos, int slot) {
		if(ws != null)
			if(ws.getTileEntity(pos) != null && ws.getTileEntity(pos) instanceof IInventory) {
				IInventory inv = (IInventory) ws.getTileEntity(pos);
				return inv.getStackInSlot(slot);
			} else if(ws.getTileEntity(pos) != null && ws.getTileEntity(pos) instanceof TileEntityJukebox) {
				TileEntityJukebox jb = (TileEntityJukebox) ws.getTileEntity(pos);
				return jb.getRecord();
			}
		return null;
	}
	
	public static void setItemStackFromInventory(ItemStack stack, WorldServer ws, BlockPos pos, int slot) {
		if(ws != null) {
			if(ws.getTileEntity(pos) != null && ws.getTileEntity(pos) instanceof IInventory) {
				IInventory inv = (IInventory) ws.getTileEntity(pos);
				inv.setInventorySlotContents(slot, stack);
				ws.markBlockForUpdate(pos);
			} else if(ws.getTileEntity(pos) != null && ws.getTileEntity(pos) instanceof TileEntityJukebox) {
				TileEntityJukebox jb = (TileEntityJukebox) ws.getTileEntity(pos);
				if(stack == null) {
					ws.playAuxSFX(1005, pos, 0);
                    ws.playRecord(pos, (String)null);
				}
				jb.setRecord(stack);
				ws.markBlockForUpdate(pos);
			}
		}
	}
	
	public static ItemStack getSoulItemStack(int temp) {
		ItemStack stack = new ItemStack(SSItems.mobSouls, 1, 0);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("Level", temp);
		return stack;
	}
}
