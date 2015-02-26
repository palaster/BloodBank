package palaster97.ss.core.helpers;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
}
