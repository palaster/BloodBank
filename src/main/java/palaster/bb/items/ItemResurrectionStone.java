package palaster.bb.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.libs.LibNBT;
import palaster.bb.world.BBWorldSaveData;

public class ItemResurrectionStone extends ItemModSpecial {

	public ItemResurrectionStone() {
		super();
		setUnlocalizedName("resurrectionStone");
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger(LibNBT.number, 0);
				return EnumActionResult.SUCCESS;
			}
			if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty()) {
				NBTTagCompound entityTag = BBWorldSaveData.get(worldIn).getDeadEntity(stack.getTagCompound().getInteger(LibNBT.number));
				if(entityTag != null) {
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
				itemStackIn.getTagCompound().setInteger(LibNBT.number, 0);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			} 
			if(playerIn.isSneaking()) {
				if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty() && BBWorldSaveData.get(worldIn).getDeadEntities().size() <= itemStackIn.getTagCompound().getInteger(LibNBT.number))
					itemStackIn.getTagCompound().setInteger(LibNBT.number, 0);
				else if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty() && BBWorldSaveData.get(worldIn).getDeadEntities().size() > itemStackIn.getTagCompound().getInteger(LibNBT.number))
					itemStackIn.getTagCompound().setInteger(LibNBT.number, itemStackIn.getTagCompound().getInteger(LibNBT.number) + 1);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			} else {
				if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty()) {
					NBTTagCompound entityTag = BBWorldSaveData.get(worldIn).getDeadEntity(itemStackIn.getTagCompound().getInteger(LibNBT.number));
					if(entityTag != null) {
						EntityLiving li = (EntityLiving) EntityList.createEntityFromNBT(entityTag, worldIn);
						if(li != null) {
							BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.misc.deadEntity") + " : " + li.getName());
							return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
						}
					}
				}
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}