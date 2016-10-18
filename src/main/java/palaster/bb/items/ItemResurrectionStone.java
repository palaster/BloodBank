package palaster.bb.items;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.world.BBWorldSaveData;
import palaster.libpal.items.ItemModSpecial;

public class ItemResurrectionStone extends ItemModSpecial {
	
	public static final String TAG_INT_SPIRIT = "SpiritNumber";

	public ItemResurrectionStone(ResourceLocation rl) { super(rl); }
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger(TAG_INT_SPIRIT, 0);
				return EnumActionResult.SUCCESS;
			}
			if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty()) {
				NBTTagCompound entityTag = BBWorldSaveData.get(worldIn).getDeadEntity(stack.getTagCompound().getInteger(TAG_INT_SPIRIT));
				if(entityTag != null)
					if(EntityList.createEntityFromNBT(entityTag, worldIn) != null)
						if(EntityList.createEntityFromNBT(entityTag, worldIn) instanceof EntityLiving) {
							EntityLiving li = (EntityLiving) EntityList.createEntityFromNBT(entityTag, worldIn);
							if(li != null) {
								li.setHealth(li.getMaxHealth());
								li.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
								worldIn.spawnEntityInWorld(li);
								BBWorldSaveData.get(worldIn).removeDeadEntity(entityTag);
								playerIn.setHeldItem(hand, null);
								return EnumActionResult.SUCCESS;
							}
						}
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			if(!itemStackIn.hasTagCompound()) {
				itemStackIn.setTagCompound(new NBTTagCompound());
				itemStackIn.getTagCompound().setInteger(TAG_INT_SPIRIT, 0);
				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
			} 
			if(playerIn.isSneaking()) {
				if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty() && BBWorldSaveData.get(worldIn).getDeadEntities().size() <= itemStackIn.getTagCompound().getInteger(TAG_INT_SPIRIT))
					itemStackIn.getTagCompound().setInteger(TAG_INT_SPIRIT, 0);
				else if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty() && BBWorldSaveData.get(worldIn).getDeadEntities().size() > itemStackIn.getTagCompound().getInteger(TAG_INT_SPIRIT))
					itemStackIn.getTagCompound().setInteger(TAG_INT_SPIRIT, itemStackIn.getTagCompound().getInteger(TAG_INT_SPIRIT) + 1);
				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
			} else {
				if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty()) {
					NBTTagCompound entityTag = BBWorldSaveData.get(worldIn).getDeadEntity(itemStackIn.getTagCompound().getInteger(TAG_INT_SPIRIT));
					if(entityTag != null) {
						EntityLiving li = (EntityLiving) EntityList.createEntityFromNBT(entityTag, worldIn);
						if(li != null) {
							BBPlayerHelper.sendChatMessageToPlayer(playerIn, net.minecraft.util.text.translation.I18n.translateToLocal("bb.misc.deadEntity") + " : " + li.getName());
							return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
						}
					}
				}
				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
