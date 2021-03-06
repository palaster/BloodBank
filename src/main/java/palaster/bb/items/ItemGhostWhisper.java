package palaster.bb.items;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.worlds.BBWorldCapability.BBWorldCapabilityProvider;
import palaster.bb.api.capabilities.worlds.IBBWorld;
import palaster.bb.core.helpers.BBPlayerHelper;
import palaster.libpal.items.ItemModSpecial;

public class ItemGhostWhisper extends ItemModSpecial {

	public ItemGhostWhisper(ResourceLocation rl) { super(rl); }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			IBBWorld bbWorld = BBWorldCapabilityProvider.get(worldIn);
			if(bbWorld != null && bbWorld.getDeadEntities() != null && !bbWorld.getDeadEntities().isEmpty()) {
				for(NBTTagCompound tag : bbWorld.getDeadEntities())
					if(EntityList.createEntityFromNBT(tag, worldIn) != null)
						if(EntityList.createEntityFromNBT(tag, worldIn) instanceof EntityLiving) {
							EntityLiving li = (EntityLiving) EntityList.createEntityFromNBT(tag, worldIn);
							BBPlayerHelper.sendChatMessageToPlayer(playerIn, net.minecraft.util.text.translation.I18n.translateToLocal("bb.misc.ghostWhisper") + " " + li.getName());							
						}
				return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
