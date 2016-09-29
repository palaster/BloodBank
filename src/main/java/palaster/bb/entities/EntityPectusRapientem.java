package palaster.bb.entities;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class EntityPectusRapientem extends EntityMob {
	
	public static final String TAG_TAG_ITEM_HANDLER = "PectusRapientemItemHandler";
	
	protected ItemStackHandler itemHandler = new ItemStackHandler(108);

	public EntityPectusRapientem(World worldIn) {
		super(worldIn);
		setSize(0.9f, 0.9f);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if(itemHandler != null)
			compound.setTag(TAG_TAG_ITEM_HANDLER, itemHandler.serializeNBT());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		itemHandler.deserializeNBT(compound.getCompoundTag(TAG_TAG_ITEM_HANDLER));
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		IBlockState bs = worldObj.getBlockState(getPosition().down());
		if(bs != null && bs.getBlock() != null && bs.getBlock().hasTileEntity(bs)) {
			TileEntity te = worldObj.getTileEntity(getPosition().down());
			if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
				IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
				if(ih != null)
					if(itemHandler.getSlots() <= ih.getSlots()) {
						int slotLeft = 0;
						for(int i = 0; i < itemHandler.getSlots(); i++) {
							if(itemHandler.getStackInSlot(i) == null)
								itemHandler.setStackInSlot(i - slotLeft, ih.getStackInSlot(i));
							else
								slotLeft++;
						}
					} else {
						int slotLeft = 0;
						for(int i = 0; i < ih.getSlots(); i++) {
							if(itemHandler.getStackInSlot(i) == null)
								itemHandler.setStackInSlot(i - slotLeft, ih.getStackInSlot(i));
							else
								slotLeft++;
						}
					}
			}
		}
	}
	
	public ItemStackHandler getItemStackHandler() { return itemHandler; }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing); }
	
	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        return super.getCapability(capability, facing);
	}
}