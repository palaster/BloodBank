package palaster.bb.core.helpers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class BBPlayerHelper {
	
	public static void sendChatMessageToPlayer(EntityPlayer player, String message) { player.addChatMessage(new TextComponentString(message)); }
	
	@Nullable
	public static EntityPlayer getPlayerFromUUID(UUID uuid) {
		for(int i = 0; i < DimensionManager.getIDs().length; i++) {
			World world = DimensionManager.getWorld(DimensionManager.getIDs()[i]);
			if(world != null && !world.isRemote)
				if(world.getPlayerEntityByUUID(uuid) != null)
					return world.getPlayerEntityByUUID(uuid);
		}
		return null;
	}
}
