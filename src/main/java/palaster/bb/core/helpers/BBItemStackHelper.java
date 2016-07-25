package palaster.bb.core.helpers;

import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import palaster.bb.blocks.BlockCommunityTool;
import palaster.bb.items.ItemBoundArmor;

public class BBItemStackHelper {

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
			} else if(ws.getTileEntity(pos) != null && ws.getTileEntity(pos) instanceof TileEntityJukebox) {
				TileEntityJukebox jb = (TileEntityJukebox) ws.getTileEntity(pos);
				if(stack == null) {
					ws.playBroadcastSound(1005, pos, 0);
                    ws.playRecord(pos, null);
				}
				jb.setRecord(stack);
			}
		}
	}
	
	public static int getItemStackSlotFromPlayer(EntityPlayer player, ItemStack stack) {
		if(player != null && !player.worldObj.isRemote && stack != null && player.inventory.hasItemStack(stack))
			for(int i = 0; i < player.inventory.getSizeInventory(); i++)
				if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == stack.getItem())
					return i;
		return -1;
	}

	public static ItemStack setItemStackInsideItemStack(ItemStack holder, ItemStack toHold, String tag) {
		if(holder != null && toHold != null && !(toHold.getItem() instanceof ItemBoundArmor)) {
			NBTTagCompound holding = new NBTTagCompound();
			toHold.writeToNBT(holding);
			if(!holder.hasTagCompound())
				holder.setTagCompound(new NBTTagCompound());
			holder.getTagCompound().setTag(tag, holding);
		}
		return holder;
	}
	
	public static ItemStack setItemStackInsideItemStackRecordPrevious(ItemStack holder, ItemStack toHold, String previousTag, String tag) {
		if(holder != null && toHold != null && !(toHold.getItem() instanceof ItemBoundArmor)) {
			NBTTagCompound holding = new NBTTagCompound();
			toHold.writeToNBT(holding);
			if(!holder.hasTagCompound())
				holder.setTagCompound(new NBTTagCompound());
			holder.getTagCompound().setTag(previousTag, holder.getTagCompound().getCompoundTag(tag));
			holder.getTagCompound().setTag(tag, holding);
		}
		return holder;
	}

	public static ItemStack getItemStackFromItemStack(ItemStack holder, String tag) {
		if(holder.hasTagCompound() && holder.getTagCompound() != null)
			return ItemStack.loadItemStackFromNBT(holder.getTagCompound().getCompoundTag(tag));
		return null;
	}
	

	public static ItemStack getPreviousItemStackFromItemStack(ItemStack holder, String previousTag) {
		if(holder != null && holder.hasTagCompound() && holder.getTagCompound() != null)
			return ItemStack.loadItemStackFromNBT(holder.getTagCompound().getCompoundTag(previousTag));
		return null;
	}

	public static ItemStack setCountDown(ItemStack stack, int timer) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean(BlockCommunityTool.tag_communityTool, true);
		stack.getItem().setMaxDamage(timer);
		return stack;
	}

	public static boolean getCountDown(ItemStack stack) {
		if(stack.hasTagCompound())
			return stack.getTagCompound().getBoolean(BlockCommunityTool.tag_communityTool);
		return false;
	}
}
