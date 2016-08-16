package palaster.bb.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityMod extends TileEntity implements ITickable {

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) { return oldState.getBlock() != newSate.getBlock(); }

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound ret = super.writeToNBT(compound);
		writePacketNBT(ret);
		return ret;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readPacketNBT(compound);
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writePacketNBT(tag);
		return super.getUpdatePacket();
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readPacketNBT(pkt.getNbtCompound());
	}
	
	public void writePacketNBT(NBTTagCompound compound) {}

	public void readPacketNBT(NBTTagCompound compound) {}
	
	public void receiveButtonEvent(int id, EntityPlayer player) {}
}
