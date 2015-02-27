package palaster97.ss.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import palaster97.ss.ScreamingSouls;
import palaster97.ss.inventories.InventoryShoeElf;

// TODO: Complete Shoe Elf Auto-Crafting
public class EntityShoeElf extends EntityLittlePeople {
	
	private InventoryShoeElf inv = new InventoryShoeElf();

	public EntityShoeElf(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public boolean interact(EntityPlayer p_70085_1_) {
		if(!worldObj.isRemote) {
			if(!p_70085_1_.isSneaking())
				p_70085_1_.openGui(ScreamingSouls.instance, 6, worldObj, 0, 0, getEntityId());
			else
				super.interact(p_70085_1_);
		}
		return true;
	}
	
	public InventoryShoeElf getInventoryShoeElf() { return inv; }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		inv.writeToNBT(tagCompound);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		inv.readFromNBT(tagCompund);
	}
}
