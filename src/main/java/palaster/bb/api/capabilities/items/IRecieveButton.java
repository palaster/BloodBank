package palaster.bb.api.capabilities.items;

import net.minecraft.entity.player.EntityPlayer;

public interface IRecieveButton {

	public void receiveButtonEvent(int buttonId, EntityPlayer player);
}
