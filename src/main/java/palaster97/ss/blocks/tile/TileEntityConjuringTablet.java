package palaster97.ss.blocks.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import palaster97.ss.recipes.ConjuringTabletRecipe;
import palaster97.ss.recipes.ConjuringTabletRecipes;

public class TileEntityConjuringTablet extends TileEntityModInventory {

	public TileEntityConjuringTablet() { super(6); }
	
	@Override
	public int getInventoryStackLimit() { return 1; }
	
	@Override
	public String getName() { return "container.conjuringTablet"; }
	
	public void trySummon(EntityPlayer player) {
		if(getStackInSlot(0) != null) {
			ItemStack major = getStackInSlot(0);
			ItemStack[] input = new ItemStack[5];
			for(int i = 1; i < getSizeInventory(); i++)
				if(getStackInSlot(i) != null)
					input[i - 1] = getStackInSlot(i);
			if(ConjuringTabletRecipes.checkRecipe(major, input)) {
				ConjuringTabletRecipe ctr = ConjuringTabletRecipes.getRecipe(major, input);
				if(ctr != null) {
					if(ctr.getName() != null && ctr.getOutput() == -1) {
						Entity entity = EntityList.createEntityByName(ctr.getName(), worldObj);
						if(entity != null && entity instanceof EntityLivingBase) {
							EntityLivingBase lb = (EntityLivingBase) entity;
							lb.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
							if(lb instanceof EntityTameable) {
								EntityTameable tameable = (EntityTameable) lb;
								tameable.setTamed(true);
								tameable.setOwnerId(player.getUniqueID().toString());
								worldObj.spawnEntityInWorld(tameable);
							} else
								worldObj.spawnEntityInWorld(lb);
							setInventorySlotContents(0, null);
							ItemStack[] toRemove = ctr.getInput().clone(); 
							for(int i = 1; i < getSizeInventory(); i++)
								for(int j = 0; j < toRemove.length; j++)
									if(getStackInSlot(i) != null && toRemove[j] != null)
										if(getStackInSlot(i).getItem() == toRemove[j].getItem()) {
											toRemove[j] = null;
											setInventorySlotContents(i, null);
										}
						}
					} else {
						Entity entity = EntityList.createEntityByID(ctr.getOutput(), worldObj);
						if(entity != null && entity instanceof EntityLivingBase) {
							EntityLivingBase lb = (EntityLivingBase) entity;
							lb.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
							if(lb instanceof EntityTameable) {
								EntityTameable tameable = (EntityTameable) lb;
								tameable.setTamed(true);
								tameable.setOwnerId(player.getUniqueID().toString());
								worldObj.spawnEntityInWorld(tameable);
							} else
								worldObj.spawnEntityInWorld(lb);
							setInventorySlotContents(0, null);
							ItemStack[] toRemove = ctr.getInput().clone(); 
							for(int i = 1; i < getSizeInventory(); i++)
								for(int j = 0; j < toRemove.length; j++)
									if(getStackInSlot(i) != null && toRemove[j] != null)
										if(getStackInSlot(i).getItem() == toRemove[j].getItem()) {
											toRemove[j] = null;
											setInventorySlotContents(i, null);
										}
						}
					}
				}
			}
		}
	}
}