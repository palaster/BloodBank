package palaster97.ss.core.helpers;

import java.util.UUID;

import palaster97.ss.items.SSItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class SSPlayerHelper {
	
	public static void sendChatMessageToPlayer(EntityPlayer player, String message) { player.addChatMessage(new ChatComponentText(message)); }
	
	public static EntityPlayer getPlayerFromDimensions(String uuid) {
		for(int i = 0; i < DimensionManager.getIDs().length; i++) {
			World world = DimensionManager.getWorld(DimensionManager.getIDs()[i]);
			if(world != null && !world.isRemote) {
				if(world.getPlayerEntityByUUID(UUID.fromString(uuid)) != null)
					return world.getPlayerEntityByUUID(UUID.fromString(uuid));
			}
		}
		return null;
	}
	
	// Memory Mechanic
	
	public static int getJournalAmount(EntityPlayer player) {
		if(!player.worldObj.isRemote)
			for(ItemStack stack : player.inventory.mainInventory)
				if(stack != null && stack.getItem() == SSItems.journal && stack.hasTagCompound())
					return stack.getTagCompound().getInteger("Level");
		return -1;
	}
	
	public static void setJournalAmount(EntityPlayer player, int amount) {
		if(!player.worldObj.isRemote)
			for(ItemStack stack : player.inventory.mainInventory)
				if(stack != null && stack.getItem() == SSItems.journal && stack.hasTagCompound())
					stack.getTagCompound().setInteger("Level", amount);
	}
	
	public static boolean reduceJournalAmount(EntityPlayer player, int amount) {
		if(!player.worldObj.isRemote)
			if(getJournalAmount(player) - amount >= 0) {
				setJournalAmount(player, getJournalAmount(player) - amount);
				return true;
			}
		return false;
	}
	
	public static boolean addJournalAmount(EntityPlayer player, int amount) {
		if(!player.worldObj.isRemote)
			if(getJournalAmount(player) + amount <= 1000) {
				setJournalAmount(player, getJournalAmount(player) + amount);
				return true;
			}
		return false;
	}
}
