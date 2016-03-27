package palaster.bb.core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.UUID;

public class BBPlayerHelper {
	
	public static void sendChatMessageToPlayer(EntityPlayer player, String message) { player.addChatMessage(new TextComponentString(message)); }
	
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
