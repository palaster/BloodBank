package palaster.bb.items;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.BloodBank;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.core.proxy.ClientProxy;
import palaster.bb.entities.careers.CareerBloodSorcerer;
import palaster.bb.entities.knowledge.BBKnowledge;

public class ItemBookBlood extends ItemModSpecial {
	
	public static final String TAG_INT_KNOWLEDGE_PIECE = "KnowledgePiece";

    public ItemBookBlood() {
        super();
        setUnlocalizedName("bookBlood");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post e) {
		if(Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().inGameHasFocus)
			if(e.getType() == ElementType.TEXT && Minecraft.getMinecraft().fontRendererObj != null) {
				final IRPG rpg = RPGCapabilityProvider.get(Minecraft.getMinecraft().thePlayer);
				if(rpg != null)
					if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
						if(Minecraft.getMinecraft().thePlayer.getHeldItemOffhand() != null && Minecraft.getMinecraft().thePlayer.getHeldItemOffhand().getItem() == this && Minecraft.getMinecraft().thePlayer.getHeldItemOffhand().hasTagCompound()) {
							Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format("bb.knowledgePiece") + ": " + I18n.format(BBKnowledge.getKnowledgePiece(Minecraft.getMinecraft().thePlayer.getHeldItemOffhand().getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).getName()), 2, 2, 0x8A0707);
							// TODO: Find a better way to sync server data to client Minecraft.getMinecraft().fontRendererObj.drawString("" + bloodBank.getCurrentBlood(), e.getResolution().getScaledWidth() - 32, e.getResolution().getScaledHeight() - 18, 0x8A0707);
							ClientProxy.isItemInOffHandRenderingOverlay = true;
						} else if(Minecraft.getMinecraft().thePlayer.getHeldItemOffhand() == null)
							ClientProxy.isItemInOffHandRenderingOverlay = false;
						if(!ClientProxy.isItemInOffHandRenderingOverlay && Minecraft.getMinecraft().thePlayer.getHeldItemMainhand() != null && Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().getItem() == this && Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().hasTagCompound()) {
							Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format("bb.knowledgePiece") + ": " + I18n.format(BBKnowledge.getKnowledgePiece(Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).getName()), 2, 2, 0x8A0707);
							// TODO: Find a better way to sync server data to client Minecraft.getMinecraft().fontRendererObj.drawString("" + bloodBank.getCurrentBlood(), e.getResolution().getScaledWidth() - 32, e.getResolution().getScaledHeight() - 18, 0x8A0707);
						}
					}
			}
	}
    
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
            tooltip.add(I18n.format("bb.knowledgePiece") + ": " + I18n.format(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).getName()));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger(TAG_INT_KNOWLEDGE_PIECE, 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote) {
        	final IRPG rpg = RPGCapabilityProvider.get(playerIn);
			if(rpg != null) {
				if(rpg.getCareer() != null && rpg.getCareer() instanceof CareerBloodSorcerer) {
		            if(itemStackIn.hasTagCompound()) {
		                if(playerIn.isSneaking()) {
		                    int temp = itemStackIn.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE);
		                    if(temp++ < BBKnowledge.getKnowledgeSize() - 1)
		                        itemStackIn.getTagCompound().setInteger(TAG_INT_KNOWLEDGE_PIECE, temp++);
		                    else if(temp++ >= BBKnowledge.getKnowledgeSize())
		                        itemStackIn.getTagCompound().setInteger(TAG_INT_KNOWLEDGE_PIECE, 0);
		                    return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		                } else if(itemStackIn.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE) >= 0) {
		                    if(BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)) != null) {
		                    	ActionResult<ItemStack> temp = BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).onKnowledgePieceRightClick(itemStackIn, worldIn, playerIn, hand);
		                    	if(temp != null && temp.getType() != null && temp.getType() == EnumActionResult.SUCCESS) {
		                    		int remainingBloodCost = ((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).getPrice());
		                    		if(remainingBloodCost > 0)
		                    			playerIn.attackEntityFrom(BloodBank.proxy.bbBlood, remainingBloodCost / 100);
		                    	}
		                    }
		                    return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		                }
		            } else {
		                itemStackIn.setTagCompound(new NBTTagCompound());
		                itemStackIn.getTagCompound().setInteger(TAG_INT_KNOWLEDGE_PIECE, 0);
		                return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		            }
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
		            if(stack.hasTagCompound() && stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE) >= 0) {
		            	if(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)) != null) {
		            		EnumActionResult temp = BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).onKnowledgePieceUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ); 
		            		if(temp != null && temp == EnumActionResult.SUCCESS) {
	            				int remainingBloodCost = ((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).getPrice());
	            				if(remainingBloodCost > 0)
	            					playerIn.attackEntityFrom(BloodBank.proxy.bbBlood, remainingBloodCost / 100);
		            		}
		            	}
		                return EnumActionResult.SUCCESS;
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
		            if(stack.hasTagCompound() && stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE) >= 0) {
		            	if(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)) != null)
		            		if(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).knowledgePieceInteractionForEntity(stack, playerIn, target, hand)) {
		            			int remainingBloodCost = ((CareerBloodSorcerer) rpg.getCareer()).consumeBlood(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(TAG_INT_KNOWLEDGE_PIECE)).getPrice());
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
