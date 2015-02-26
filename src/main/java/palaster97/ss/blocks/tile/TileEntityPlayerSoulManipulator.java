package palaster97.ss.blocks.tile;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.entities.extended.SoulNetworkExtendedPlayer;
import palaster97.ss.items.ItemSoulBinder;
import palaster97.ss.items.SSItems;

public class TileEntityPlayerSoulManipulator extends TileEntityMod implements IUpdatePlayerListBox {

	public TileEntityPlayerSoulManipulator() { super(3); }

	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public String getName() { return "container.playerSoulManipulator"; }

	@Override
	public void update() {
		if(!worldObj.isRemote) {
			if(getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemSoulBinder) {
				if(getStackInSlot(0).hasTagCompound()) {
					EntityPlayer p = SSPlayerHelper.getPlayerFromDimensions(getStackInSlot(0).getTagCompound().getString("PlayerUUID"));
					if(p != null) {
						if(getStackInSlot(1) != null && getStackInSlot(1).getItem() == new ItemStack(SSItems.mobSouls, 1, 1).getItem()) {
							if(getStackInSlot(1).hasTagCompound()) {
								EntityPlayer p1 = null;
								for(int i = 0; i < DimensionManager.getIDs().length; i++) {
									World world = DimensionManager.getWorld(DimensionManager.getIDs()[i]);
									if(world != null && !world.isRemote) {
										if(world.getPlayerEntityByUUID(UUID.fromString(getStackInSlot(1).getTagCompound().getString("PlayerUUID"))) != null) {
											p1 = world.getPlayerEntityByUUID(UUID.fromString(getStackInSlot(1).getTagCompound().getString("PlayerUUID")));
											if(getStackInSlot(2) != null && getStackInSlot(2).getItem() instanceof ItemPotion) {
												ItemPotion potion = (ItemPotion) getStackInSlot(2).getItem();
												List potionEffects = potion.getEffects(getStackInSlot(2));
												if(potionEffects != null) {
													Iterator iterator = potionEffects.iterator();
													while(iterator.hasNext()) {
									                    PotionEffect potioneffect = (PotionEffect)iterator.next();
									                    PotionEffect pe1 = new PotionEffect(potioneffect.getPotionID(), 20, potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.getIsAmbient());
									                    p1.addPotionEffect(pe1);
									                }
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
