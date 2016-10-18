package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.BloodBank;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.core.helpers.NBTHelper;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.knowledge.BBKnowledge;
import palaster.libpal.items.ItemModSpecial;

public class ItemBookBlood extends ItemModSpecial {
	
	public static final String TAG_INT_KNOWLEDGE_PIECE = "KnowledgePiece";

    public ItemBookBlood(ResourceLocation rl) { super(rl); }
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	if(!worldIn.isRemote)
    		if(entityIn instanceof EntityLivingBase)
    			if(stack.getItemDamage() > 0)
    				stack.damageItem(-1, (EntityLivingBase) entityIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(stack.hasTagCompound() && stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE) >= 0)
            tooltip.add(I18n.format("bb.knowledgePiece") + ": " + I18n.format(BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE)).getName()));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) { stack = NBTHelper.setIntegerToItemStack(stack, TAG_INT_KNOWLEDGE_PIECE, 0); }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote) {
        	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null) {
				if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
		            if(itemStackIn.hasTagCompound()) {
		                if(playerIn.isSneaking()) {
		                    int temp = NBTHelper.getIntegerFromItemStack(itemStackIn, TAG_INT_KNOWLEDGE_PIECE);
		                    if(temp++ < BBKnowledge.getKnowledgeSize() - 1)
		                    	itemStackIn = NBTHelper.setIntegerToItemStack(itemStackIn, TAG_INT_KNOWLEDGE_PIECE, temp++);
		                    else if(temp++ >= BBKnowledge.getKnowledgeSize())
		                    	itemStackIn = NBTHelper.setIntegerToItemStack(itemStackIn, TAG_INT_KNOWLEDGE_PIECE, 0);
		                    return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		                } else if(NBTHelper.getIntegerFromItemStack(itemStackIn, TAG_INT_KNOWLEDGE_PIECE) >= 0)
		                    if(BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(itemStackIn, TAG_INT_KNOWLEDGE_PIECE)) != null) {
		                    	ActionResult<ItemStack> temp = BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).onKnowledgePieceRightClick(itemStackIn, worldIn, playerIn, hand);
		                    	if(temp != null && temp.getType() != null && temp.getType() == EnumActionResult.SUCCESS) {
		                    		int remainingBloodCost = ((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).getPrice());
		                    		if(remainingBloodCost > 0)
		                    			playerIn.attackEntityFrom(BloodBank.proxy.bbBlood, remainingBloodCost / 100);
		                    		return temp;
		                    	}
		                    }
		            } else
		                return ActionResult.newResult(EnumActionResult.SUCCESS, NBTHelper.setIntegerToItemStack(itemStackIn, TAG_INT_KNOWLEDGE_PIECE, 0));
				}
			}
		}
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
        	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null)
				if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer)
		            if(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE) >= 0)
		            	if(BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE)) != null) {
		            		EnumActionResult temp = BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE)).onKnowledgePieceUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ); 
		            		if(temp != null && temp == EnumActionResult.SUCCESS) {
	            				int remainingBloodCost = ((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE)).getPrice());
	            				if(remainingBloodCost > 0)
	            					playerIn.attackEntityFrom(BloodBank.proxy.bbBlood, remainingBloodCost / 100);
	            				return temp;
		            		}
		            	}
		}
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.worldObj.isRemote) {
        	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null) {
				if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer)
		            if(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE) >= 0) {
		            	if(BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE)) != null)
		            		if(BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE)).knowledgePieceInteractionForEntity(stack, playerIn, target, hand)) {
		            			int remainingBloodCost = ((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(BBKnowledge.getKnowledgePiece(NBTHelper.getIntegerFromItemStack(stack, TAG_INT_KNOWLEDGE_PIECE)).getPrice());
		            			if(remainingBloodCost > 0)
		            				playerIn.attackEntityFrom(BloodBank.proxy.bbBlood, remainingBloodCost / 100);
		            		}
		                return true;
		            }
			}
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
