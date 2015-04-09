package palaster97.ss.blocks.tile;

import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityPlayerSoulManipulator extends TileEntityModInventory implements IUpdatePlayerListBox {

	public TileEntityPlayerSoulManipulator() { super(11); }

	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public String getName() { return "container.playerSoulManipulator"; }

	@Override
	public void update() {
		if(!worldObj.isRemote) {}
	}
}
