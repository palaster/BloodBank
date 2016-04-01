package palaster.bb.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

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
					ws.playAuxSFX(1005, pos, 0);
                    ws.playRecord(pos, null);
				}
				jb.setRecord(stack);
				// TODO: Look into if this effects anything : Removed markBlockForUpdate(BlockPos) from WorldServer
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
		if(toHold != null) {
			NBTTagCompound holding = new NBTTagCompound();
			toHold.writeToNBT(holding);
			holder.setTagCompound(new NBTTagCompound());
			holder.getTagCompound().setTag("BBItemHolder", holding);
		}
		return holder;
	}

	public static ItemStack getItemStackFromItemStack(ItemStack holder) {
		if(holder.hasTagCompound())
			if(holder.getTagCompound() != null)
				return ItemStack.loadItemStackFromNBT(holder.getTagCompound().getCompoundTag("BBItemHolder"));
		return null;
	}

	public static List<ItemStack> getItemStacksFromOreDictionary(String ore, int amt) {
		if(OreDictionary.doesOreNameExist(ore)) {
			List<ItemStack> itemStacks = OreDictionary.getOres(ore);
			List<ItemStack> newStacks = new ArrayList<ItemStack>();
			if(itemStacks != null)
				for(ItemStack stack : itemStacks) {
					if(Block.getBlockFromItem(stack.getItem()) == Blocks.planks)
						newStacks.add(new ItemStack(stack.getItem(), amt, Short.MAX_VALUE));
					else
						newStacks.add(new ItemStack(stack.getItem(), amt, stack.getItemDamage()));
				}
			return newStacks;
		}
		return null;
	}
}
