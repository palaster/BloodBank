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
import palaster.bb.libs.LibNBT;

public class ItemFlames extends ItemModSpecial {

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
					if(copy.getTagCompound().getInteger(LibNBT.flameSet) == 0) {
						copy.getTagCompound().setInteger(LibNBT.flameSet, 1);
						BBItemStackHelper.setFirstSpellInsideFlames(copy, e.getRight());
					} else if(copy.getTagCompound().getInteger(LibNBT.flameSet) == 1) {
						copy.getTagCompound().setInteger(LibNBT.flameSet, 2);
						BBItemStackHelper.setSpellInsideFlames(copy, e.getRight());
					} else if(copy.getTagCompound().getInteger(LibNBT.flameSet) == 2)
						BBItemStackHelper.setSpellInsideFlames(copy, e.getRight());
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
						if(BBItemStackHelper.getPreviousSpellFromFlames(e.getRight()) != null && e.getRight().getTagCompound().getInteger(LibNBT.flameSet) == 2)
							e.getEntityPlayer().worldObj.spawnEntityInWorld(new EntityItem(e.getEntityPlayer().worldObj, e.getEntityPlayer().posX, e.getEntityPlayer().posY + .1, e.getEntityPlayer().posZ, BBItemStackHelper.getPreviousSpellFromFlames(e.getRight())));
	}

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(!player.worldObj.isRemote)
            if(BBItemStackHelper.getSpellFromFlames(stack) != null && BBItemStackHelper.getSpellFromFlames(stack).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(player);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).getSpellCost())
                            if(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).onSpellLeftClickEntity(stack, player, entity))
                            	undead.useFocus(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).getSpellCost());
            }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getSpellFromFlames(stack) != null && BBItemStackHelper.getSpellFromFlames(stack).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).getSpellCost())
            				if(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).onSpellUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS)
            					undead.useFocus(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).getSpellCost());
            }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getSpellFromFlames(itemStackIn) != null && BBItemStackHelper.getSpellFromFlames(itemStackIn).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getSpellFromFlames(itemStackIn).getItem()).getSpellCost())
            				if(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(itemStackIn).getItem()).onSpellRightClick(itemStackIn, worldIn, playerIn, hand).getType() == EnumActionResult.SUCCESS)
            					undead.useFocus(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(itemStackIn).getItem()).getSpellCost());
            }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.worldObj.isRemote)
            if(BBItemStackHelper.getSpellFromFlames(stack) != null && BBItemStackHelper.getSpellFromFlames(stack).getItem() instanceof IFlameSpell) {
            	final IUndead undead = UndeadCapabilityProvider.get(playerIn);
            	if(undead != null)
            		if(undead.isUndead())
            			if(undead.getFocus() >= ((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).getSpellCost())
                            if(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).spellInteractionForEntity(stack, playerIn, target, hand))
                            	undead.useFocus(((IFlameSpell) BBItemStackHelper.getSpellFromFlames(stack).getItem()).getSpellCost());
            }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
