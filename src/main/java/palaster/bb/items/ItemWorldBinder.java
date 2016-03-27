package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemWorldBinder extends ItemModSpecial {

	public ItemWorldBinder() {
		super();
		setUnlocalizedName("worldBinder");
	}
	
	@Override
	public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
		if(!p_77622_1_.hasTagCompound())
			p_77622_1_.setTagCompound(new NBTTagCompound());
		p_77622_1_.getTagCompound().setBoolean("IsSet", false);
		p_77622_1_.getTagCompound().setIntArray("WorldPos", new int[]{0, 0, 0});
		p_77622_1_.getTagCompound().setInteger("DimID", 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
		if(p_77624_1_.hasTagCompound()) {
			if(p_77624_1_.getTagCompound().getBoolean("IsSet")) {
				int[] temp = p_77624_1_.getTagCompound().getIntArray("WorldPos");
				p_77624_3_.add(I18n.translateToLocal("bb.misc.pos") + ": " + temp[0] + " " + temp[1] + " " + temp[2]);
				p_77624_3_.add(I18n.translateToLocal("bb.misc.dimensionID") + ": " + p_77624_1_.getTagCompound().getInteger("DimID"));
			}
		} else 
			p_77624_3_.add(I18n.translateToLocal("bb.misc.broken"));
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(stack.hasTagCompound()) {
				stack.getTagCompound().setBoolean("IsSet", true);
				stack.getTagCompound().setIntArray("WorldPos", new int[]{pos.getX(), pos.getY(), pos.getZ()});
				stack.getTagCompound().setInteger("DimID", worldIn.provider.getDimension());
				return EnumActionResult.SUCCESS;
			} else {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setBoolean("IsSet", true);
				stack.getTagCompound().setIntArray("WorldPos", new int[]{pos.getX(), pos.getY(), pos.getZ()});
				stack.getTagCompound().setInteger("DimID", worldIn.provider.getDimension());
				return EnumActionResult.SUCCESS;
			}
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
