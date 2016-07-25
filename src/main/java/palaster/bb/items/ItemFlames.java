package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.api.capabilities.entities.IUndead;
import palaster.bb.api.capabilities.entities.UndeadCapability.UndeadCapabilityProvider;
import palaster.bb.api.capabilities.items.IFlameSpell;
import palaster.bb.core.helpers.BBItemStackHelper;

public class ItemFlames extends ItemModSpecial {
	
	public static String tag_flameState = "FlameState";
	public static String tag_flameHolder = "FlameHolder";
    public static String tag_previousFlameHolder = "PreviousFlameHolder";

    public ItemFlames() {
        super();
        setUnlocalizedName("flames");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e) {
		if(e.getLeft() != null && e.getRight() != null) {
			ItemStack copy = e.getLeft().copy();
			if(e.getLeft().getItem() instanceof ItemFlames)
				if(e.getRight().getItem() instanceof IFlameSpell) {
					copy = e.getLeft().copy();
					if(!copy.hasTagCompound())
						copy.setTagCompound(new NBTTagCompound());
					if(copy.getTagCompound().getInteger(tag_flameState) == 0) {
						copy.getTagCompound().setInteger(tag_flameState, 1);
						BBItemStackHelper.setItemStackInsideItemStack(copy, e.getRight(), tag_flameHolder);
					} else if(copy.getTagCompound().getInteger(tag_flameState) == 1) {
						copy.getTagCompound().setInteger(tag_flameState, 2);
						BBItemStackHelper.setItemStackInsideItemStackRecordPrevious(copy, e.getRight(), tag_previousFlameHolder, tag_flameHolder);
					} else if(copy.getTagCompound().getInteger(tag_flameState) == 2)
						BBItemStackHelper.setItemStackInsideItemStackRecordPrevious(copy, e.getRight(), tag_previousFlameHolder, tag_flameHolder);
					e.setMaterialCost(1);
					e.setCost(1);
					e.setOutput(copy);
				}
		}
	}
    
    @SubscribeEvent
	public void onAnvilRepair(AnvilRepairEvent e) {
		// getLeft() is Right Slot and getRight() is Left Slot.
		if(!e.getEntityPlayer().worldObj.isRemote)
			if(e.getLeft() != null && e.getRight() != null)
				if(e.getRight().getItem() instanceof ItemFlames && e.getLeft().getItem() instanceof IFlameSpell)
					if(e.getRight().hasTagCompound())
						if(BBItemStackHelper.getPreviousItemStackFromItemStack(e.getRight(), tag_previousFlameHolder) != null && e.getRight().getTagCompound().getInteger(tag_flameState) == 2)
							e.getEntityPlayer().worldObj.spawnEntityInWorld(new EntityItem(e.getEntityPlayer().worldObj, e.getEntityPlayer().posX, e.getEntityPlayer().posY + .1, e.getEntityPlayer().posZ, BBItemStackHelper.getPreviousItemStackFromItemStack(e.getRight(), tag_previousFlameHolder)));
	}

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(!player.worldObj.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder) != null && BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(player);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).getSpellCost()) {
            				boolean temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).onSpellLeftClickEntity(stack, player, entity);
            				if(temp)
            					undead.useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder) != null && BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).getSpellCost()) {
            				EnumActionResult temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).onSpellUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            				if(temp != null && temp == EnumActionResult.SUCCESS)
            					undead.useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(itemStackIn, tag_flameHolder) != null && BBItemStackHelper.getItemStackFromItemStack(itemStackIn, tag_flameHolder).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn, tag_flameHolder).getItem()).getSpellCost()) {
            				ActionResult<ItemStack> temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn, tag_flameHolder).getItem()).onSpellRightClick(itemStackIn, worldIn, playerIn, hand); 
            				if(temp != null && temp.getType() == EnumActionResult.SUCCESS)
            					undead.useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn, tag_flameHolder).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.worldObj.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder) != null && BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).getSpellCost()) {
            				boolean temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).spellInteractionForEntity(stack, playerIn, target, hand);
            				if(temp)
                            	undead.useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, tag_flameHolder).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}