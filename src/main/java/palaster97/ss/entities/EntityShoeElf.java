package palaster97.ss.entities;

import palaster97.ss.ScreamingSouls;
import palaster97.ss.inventories.InventoryShoeElf;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

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
