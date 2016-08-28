package palaster.bb.items;

import net.minecraft.block.BlockBed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.items.IPurified;
import palaster.bb.entities.careers.CareerCleric;

public class ItemClericStaff extends ItemModStaff implements IPurified {
	
	public ItemClericStaff() {
		super();
		powers = new String[]{"bb.clericStaff.0", "bb.clericStaff.1", "bb.clericStaff.2", "bb.clericStaff.3", "bb.clericStaff.4"};
		setMaxDamage(256);
		setUnlocalizedName("clericStaff");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPlayerUseBlock(PlayerInteractEvent.RightClickBlock e) {
		if(!e.getWorld().isRemote)
			if(e.getEntityPlayer().isSneaking())
				if(e.getWorld().getBlockState(e.getPos()) != null && e.getWorld().getBlockState(e.getPos()).getBlock() instanceof BlockBed)
					if(e.getItemStack() != null && e.getItemStack().getItem() == this) {
						final IRPG rpg = RPGCapabilityProvider.get(e.getEntityPlayer());
						if(rpg != null)
							if(rpg.getCareer() == null)
								rpg.setCareer(new CareerCleric());
					}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand); }
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ); }
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!player.worldObj.isRemote)
			if(entity instanceof EntityLiving) {
				final IRPG rpg = RPGCapabilityProvider.get(player);
				if(rpg != null)
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerCleric)
						if(((EntityLiving) entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
							return ((EntityLiving) entity).attackEntityFrom(DamageSource.causePlayerDamage(player), (float) (player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + 5));
			}
		return super.onLeftClickEntity(stack, player, entity);
	}
}
