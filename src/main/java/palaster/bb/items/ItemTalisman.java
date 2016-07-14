package palaster.bb.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.EntityTalisman;

public class ItemTalisman extends ItemMod {

	public ItemTalisman() {
		super();
		setMaxDamage(3);
		setMaxStackSize(64);
		setUnlocalizedName("talisman");
		addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) { return stack.getItemDamage(); }
        });
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.getItemDamage() > 0)
			switch(stack.getItemDamage()) {
				case 1: {
					tooltip.add(I18n.format("bb.talisman.speed"));
					break;
				}
				case 2: {
					tooltip.add(I18n.format("bb.talisman.poison"));
					break;
				}
			}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(itemStackIn.getItemDamage() > 0) {
	        if(!playerIn.capabilities.isCreativeMode)
	            --itemStackIn.stackSize;
	        worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        if(!worldIn.isRemote) {
	            EntityTalisman talisman = new EntityTalisman(worldIn, playerIn, itemStackIn.getItemDamage());
	            talisman.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
	            worldIn.spawnEntityInWorld(talisman);
	        }
		}
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) { return false; }
}
