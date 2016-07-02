package palaster.bb.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.bb.world.BBWorldSaveData;

public class ItemGhostWhisper extends ItemModSpecial {

	public ItemGhostWhisper() {
		super();
		setUnlocalizedName("ghostWhisper");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote)
			if(BBWorldSaveData.get(worldIn) != null && BBWorldSaveData.get(worldIn).getDeadEntities() != null && !BBWorldSaveData.get(worldIn).getDeadEntities().isEmpty()) {
				for(NBTTagCompound tag : BBWorldSaveData.get(worldIn).getDeadEntities())
					if(EntityList.createEntityFromNBT(tag, worldIn) != null) {
						if(EntityList.createEntityFromNBT(tag, worldIn) instanceof EntityLiving) {
							EntityLiving li = (EntityLiving) EntityList.createEntityFromNBT(tag, worldIn);
							BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.misc.ghostWhisper") + li.getName());							
						} else if(EntityList.createEntityFromNBT(tag, worldIn) instanceof EntityPlayer) {
							EntityPlayer p = (EntityPlayer) EntityList.createEntityFromNBT(tag, worldIn);
							BBPlayerHelper.sendChatMessageToPlayer(playerIn, I18n.format("bb.misc.ghostWhisper") + p.getName());
						}
					}
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
