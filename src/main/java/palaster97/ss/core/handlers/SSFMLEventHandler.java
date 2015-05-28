package palaster97.ss.core.handlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Mouse;

import palaster97.ss.network.PacketHandler;
import palaster97.ss.network.server.MiddleClickMessage;

public class SSFMLEventHandler {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouseInput(MouseInputEvent e) {
		if(Minecraft.getMinecraft().inGameHasFocus)
			if(Mouse.isButtonDown(2))
				PacketHandler.sendToServer(new MiddleClickMessage());
	}
}
