package palaster97.ss.runes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class RuneOpenDoor extends Rune {

	public RuneOpenDoor(int runeID, ItemStack id) {
		super(runeID, id);
	}

	@Override
	public void activate(World world, BlockPos pos, EntityPlayer player) {
		MovingObjectPosition mop = player.rayTrace(6, 1.0f);
		if(mop != null) {
			if(mop.typeOfHit == MovingObjectType.BLOCK) {
				Block block = world.getBlockState(mop.getBlockPos()).getBlock();
				if(block != null && block instanceof BlockDoor) {
					BlockDoor door = (BlockDoor) block;
					if(door != null && !BlockDoor.isOpen(world, mop.getBlockPos()))
						if(!world.isRemote)
							door.toggleDoor(world, mop.getBlockPos(), true);
				}
			}
		}
	}
}