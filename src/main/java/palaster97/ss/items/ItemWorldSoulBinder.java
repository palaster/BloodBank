package palaster97.ss.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWorldSoulBinder extends ItemModSpecial {

	public ItemWorldSoulBinder() {
		super();
		setUnlocalizedName("worldSoulBinder");
	}
	
	@Override
	public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
		if(!p_77622_1_.hasTagCompound())
			p_77622_1_.setTagCompound(new NBTTagCompound());
		p_77622_1_.getTagCompound().setBoolean("IsSet", false);
		p_77622_1_.getTagCompound().setIntArray("WorldPos", new int[]{0, 0, 0});
		p_77622_1_.getTagCompound().setInteger("DimID", 0);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
		if(p_77624_1_.hasTagCompound()) {
			if(p_77624_1_.getTagCompound().getBoolean("IsSet")) {
				int[] temp = p_77624_1_.getTagCompound().getIntArray("WorldPos");
				p_77624_3_.add(StatCollector.translateToLocal("ss.misc.pos") + ": " + temp[0] + " " + temp[1] + " " + temp[2]);
				p_77624_3_.add(StatCollector.translateToLocal("ss.misc.dimensionID") + ": " + p_77624_1_.getTagCompound().getInteger("DimID"));
			}
		} else 
			p_77624_3_.add(StatCollector.translateToLocal("ss.misc.broken"));
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(stack.hasTagCompound()) {
				stack.getTagCompound().setBoolean("IsSet", true);
				stack.getTagCompound().setIntArray("WorldPos", new int[]{pos.getX(), pos.getY(), pos.getZ()});
				stack.getTagCompound().setInteger("DimID", worldIn.provider.getDimensionId());
				return true;
			} else {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setBoolean("IsSet", true);
				stack.getTagCompound().setIntArray("WorldPos", new int[]{pos.getX(), pos.getY(), pos.getZ()});
				stack.getTagCompound().setInteger("DimID", worldIn.provider.getDimensionId());
				return true;
			}
		}
		return false;
	}
}
