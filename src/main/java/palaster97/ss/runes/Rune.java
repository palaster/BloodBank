package palaster97.ss.runes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class Rune {

	public static final Rune[] runes = new Rune[256];
	public final int runeID;
	
	public Rune(int runeID) {
		this.runeID = runeID;
		if(runes[runeID] != null)
            throw new IllegalArgumentException("Duplicate ritual id! " + getClass() + " and " + runes[runeID].getClass() + " Rune ID:" + runeID);
        else
        	runes[runeID] = this;
	}
	
	public abstract void activate(World world, BlockPos pos, EntityPlayer player);
}
