package palaster.bb.core.helpers;

import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import palaster.bb.items.BoundArmor;
import palaster.bb.libs.LibNBT;

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

	public static ItemStack setItemStackInsideItemStack(ItemStack holder, ItemStack toHold) {
		if(toHold != null && !(toHold.getItem() instanceof BoundArmor)) {
			NBTTagCompound holding = new NBTTagCompound();
			toHold.writeToNBT(holding);
			if(!holder.hasTagCompound())
				holder.setTagCompound(new NBTTagCompound());
			holder.getTagCompound().setTag(LibNBT.holderItem, holding);
		}
		return holder;
	}

	public static ItemStack getItemStackFromItemStack(ItemStack holder) {
		if(holder.hasTagCompound() && holder.getTagCompound() != null)
			return ItemStack.loadItemStackFromNBT(holder.getTagCompound().getCompoundTag(LibNBT.holderItem));
		return null;
	}

	public static ItemStack setFirstSpellInsideFlames(ItemStack flames, ItemStack spell) {
		if(flames != null && spell != null) {
			NBTTagCompound holding = new NBTTagCompound();
			spell.writeToNBT(holding);
			if(!flames.hasTagCompound())
				flames.setTagCompound(new NBTTagCompound());
			flames.getTagCompound().setTag(LibNBT.holderFlame, holding);
		}
		return flames;
	}

	public static ItemStack setSpellInsideFlames(ItemStack flames, ItemStack spell) {
		if(flames != null && spell != null) {
			NBTTagCompound holding = new NBTTagCompound();
			spell.writeToNBT(holding);
			if(!flames.hasTagCompound())
				flames.setTagCompound(new NBTTagCompound());
			flames.getTagCompound().setTag(LibNBT.previousHolderFlame, flames.getTagCompound().getCompoundTag(LibNBT.holderFlame));
			flames.getTagCompound().setTag(LibNBT.holderFlame, holding);
		}
		return flames;
	}

	public static ItemStack getSpellFromFlames(ItemStack flames) {
		if(flames != null && flames.hasTagCompound() && flames.getTagCompound() != null)
			return ItemStack.loadItemStackFromNBT(flames.getTagCompound().getCompoundTag(LibNBT.holderFlame));
		return null;
	}

	public static ItemStack getPreviousSpellFromFlames(ItemStack flames) {
		if(flames != null && flames.hasTagCompound() && flames.getTagCompound() != null)
			return ItemStack.loadItemStackFromNBT(flames.getTagCompound().getCompoundTag(LibNBT.previousHolderFlame));
		return null;
	}

	public static ItemStack setCountDown(ItemStack stack, int timer) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean(LibNBT.communityItem, true);
		stack.getItem().setMaxDamage(timer);
		return stack;
	}

	public static boolean getCountDown(ItemStack stack) {
		if(stack.hasTagCompound())
			return stack.getTagCompound().getBoolean(LibNBT.communityItem);
		return false;
	}
}
