package palaster.bb.items;

import java.util.List;

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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.items.IFlameSpell;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.entities.careers.CareerUnkindled;

public class ItemFlames extends ItemModSpecial {

	public static final String TAG_TAG_FLAME_HOLDER = "FlameHolder";
    public static final String TAG_TAG_PREVIOUS_FLAME_HOLDER = "PreviousFlameHolder";

    public ItemFlames() {
        super();
        setUnlocalizedName("flames");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e) {
		if(e.getLeft() != null && e.getRight() != null)
			if(e.getLeft().getItem() instanceof ItemFlames)
				if(e.getRight().getItem() instanceof IFlameSpell) {
					ItemStack copy = e.getLeft().copy();
					if(!copy.hasTagCompound())
						copy.setTagCompound(new NBTTagCompound());
					if(BBItemStackHelper.getItemStackFromItemStack(copy, TAG_TAG_FLAME_HOLDER) == null)
						copy = BBItemStackHelper.setItemStackInsideItemStack(copy, e.getRight(), TAG_TAG_FLAME_HOLDER);
					else if(BBItemStackHelper.getItemStackFromItemStack(copy, TAG_TAG_FLAME_HOLDER) != null && BBItemStackHelper.getItemStackFromItemStack(copy, TAG_TAG_PREVIOUS_FLAME_HOLDER) == null)
						copy = BBItemStackHelper.setItemStackInsideItemStackRecordPrevious(copy, e.getRight(), TAG_TAG_PREVIOUS_FLAME_HOLDER, TAG_TAG_FLAME_HOLDER);
					else
						copy = BBItemStackHelper.setItemStackInsideItemStackRecordPrevious(copy, e.getRight(), TAG_TAG_PREVIOUS_FLAME_HOLDER, TAG_TAG_FLAME_HOLDER);
					e.setMaterialCost(1);
					e.setCost(1);
					e.setOutput(copy);
				}
	}
    
    @SubscribeEvent
	public void onAnvilRepair(AnvilRepairEvent e) {
		if(!e.getEntityPlayer().worldObj.isRemote)
			if(e.getItemInput() != null && e.getIngredientInput() != null && e.getItemResult() != null)
				if(e.getItemInput().getItem() instanceof ItemFlames && e.getIngredientInput().getItem() instanceof IFlameSpell && e.getItemResult().getItem() instanceof ItemFlames)
					if(e.getItemResult().hasTagCompound())
						if(BBItemStackHelper.getItemStackFromItemStack(e.getItemResult(), TAG_TAG_PREVIOUS_FLAME_HOLDER) != null)
							e.getEntityPlayer().worldObj.spawnEntityInWorld(new EntityItem(e.getEntityPlayer().worldObj, e.getEntityPlayer().posX, e.getEntityPlayer().posY + .1, e.getEntityPlayer().posZ, BBItemStackHelper.getItemStackFromItemStack(e.getItemResult(), TAG_TAG_PREVIOUS_FLAME_HOLDER)));
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    	if(BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER) != null && BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem() instanceof IFlameSpell)
    		tooltip.add(BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getDisplayName());
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(!player.worldObj.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER) != null && BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem() instanceof IFlameSpell) {
            	final IRPG rpg = RPGCapabilityProvider.get(player);
            	if(rpg != null)
            		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
            			if(((CareerUnkindled) rpg.getCareer()).getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost()) {
            				boolean temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).onSpellLeftClickEntity(stack, player, entity);
            				if(temp)
            					((CareerUnkindled) rpg.getCareer()).useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER) != null && BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem() instanceof IFlameSpell) {
            	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
            	if(rpg != null)
            		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
            			if(((CareerUnkindled) rpg.getCareer()).getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost()) {
            				EnumActionResult temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).onSpellUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            				if(temp != null && temp == EnumActionResult.SUCCESS)
            					((CareerUnkindled) rpg.getCareer()).useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(itemStackIn, TAG_TAG_FLAME_HOLDER) != null && BBItemStackHelper.getItemStackFromItemStack(itemStackIn, TAG_TAG_FLAME_HOLDER).getItem() instanceof IFlameSpell) {
            	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
            	if(rpg != null)
            		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
            			if(((CareerUnkindled) rpg.getCareer()).getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost()) {
            				ActionResult<ItemStack> temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn, TAG_TAG_FLAME_HOLDER).getItem()).onSpellRightClick(itemStackIn, worldIn, playerIn, hand); 
            				if(temp != null && temp.getType() == EnumActionResult.SUCCESS)
            					((CareerUnkindled) rpg.getCareer()).useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(itemStackIn, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.worldObj.isRemote)
            if(BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER) != null && BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem() instanceof IFlameSpell) {
            	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
            	if(rpg != null)
            		if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerUnkindled)
            			if(((CareerUnkindled) rpg.getCareer()).getFocus() >= ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost()) {
            				boolean temp = ((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).spellInteractionForEntity(stack, playerIn, target, hand);
            				if(temp)
            					((CareerUnkindled) rpg.getCareer()).useFocus(((IFlameSpell) BBItemStackHelper.getItemStackFromItemStack(stack, TAG_TAG_FLAME_HOLDER).getItem()).getSpellCost());
            				return temp;
            			}
            }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}