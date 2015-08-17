package palaster97.ss.blocks.tile;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import palaster97.ss.core.helpers.SSPlayerHelper;

public class TileEntityPlayerManipulator extends TileEntityModInventory implements IUpdatePlayerListBox {

	public TileEntityPlayerManipulator() { super(3); }

	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public String getName() { return "container.playerManipulator"; }
	
	@SuppressWarnings("unchecked")
	@Override
	public void update() {
		if(!worldObj.isRemote) {
			if(getStackInSlot(0) != null && getStackInSlot(1) != null && getStackInSlot(0).hasTagCompound() && getStackInSlot(1).hasTagCompound()) {
				EntityPlayer predator = SSPlayerHelper.getPlayerFromDimensions(getStackInSlot(0).getTagCompound().getString("PlayerUUID"));
				EntityPlayer prey = SSPlayerHelper.getPlayerFromDimensions(getStackInSlot(1).getTagCompound().getString("PlayerUUID"));
				if(predator != null && prey != null) {
					if(getStackInSlot(2) != null && getStackInSlot(2).getItem() instanceof ItemPotion) {
						ItemPotion pt = (ItemPotion) getStackInSlot(2).getItem();
						List<PotionEffect> pes = pt.getEffects(getStackInSlot(2));
						for(PotionEffect effect : pes)
							if(effect != null)
								prey.addPotionEffect(effect);
						setInventorySlotContents(2, null);
					}
				}
			}
		}
	}
}
