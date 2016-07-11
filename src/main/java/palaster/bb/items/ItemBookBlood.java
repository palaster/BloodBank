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
import palaster.bb.api.BBApi;
import palaster.bb.core.proxy.ClientProxy;
import palaster.bb.entities.knowledge.BBKnowledge;
import palaster.bb.libs.LibNBT;

public class ItemBookBlood extends ItemModSpecial {

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
				EntityPlayer p = Minecraft.getMinecraft().thePlayer;
				if(p != null) {
					if(p.getHeldItemOffhand() != null && p.getHeldItemOffhand().getItem() == this && p.getHeldItemOffhand().hasTagCompound()) {
						ItemStack book = p.getHeldItemOffhand();
						Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format("bb.knowledgePiece") + ": " + I18n.format(BBKnowledge.getKnowledgePiece(book.getTagCompound().getInteger(LibNBT.knowledgePiece)).getName()), 2, 2, 0x8A0707);
						Minecraft.getMinecraft().fontRendererObj.drawString("" + BBApi.getCurrentBlood(p), e.getResolution().getScaledWidth() - 32, e.getResolution().getScaledHeight() - 18, 0x8A0707);
						ClientProxy.isItemInOffHandRenderingOverlay = true;
					} else if(p.getHeldItemOffhand() == null)
						ClientProxy.isItemInOffHandRenderingOverlay = false;
					if(!ClientProxy.isItemInOffHandRenderingOverlay && p.getHeldItemMainhand() != null && p.getHeldItemMainhand().getItem() == this && p.getHeldItemMainhand().hasTagCompound()) {
						ItemStack book = p.getHeldItemMainhand();
						Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format("bb.knowledgePiece") + ": " + I18n.format(BBKnowledge.getKnowledgePiece(book.getTagCompound().getInteger(LibNBT.knowledgePiece)).getName()), 2, 2, 0x8A0707);
						Minecraft.getMinecraft().fontRendererObj.drawString("" + BBApi.getCurrentBlood(p), e.getResolution().getScaledWidth() - 32, e.getResolution().getScaledHeight() - 18, 0x8A0707);
					}
				}
			}
	}
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	if(!worldIn.isRemote) {
    		if(entityIn instanceof EntityLivingBase)
    			if(stack.getItemDamage() > 0)
    				stack.damageItem(-1, (EntityLivingBase) entityIn);
    		if(isSelected && entityIn instanceof EntityPlayer)
    			BBApi.syncServerToClient((EntityPlayer) entityIn);
    	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(stack.hasTagCompound() && stack.getTagCompound().getInteger(LibNBT.knowledgePiece) >= 0)
            tooltip.add(I18n.format("bb.knowledgePiece") + ": " + I18n.format(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(LibNBT.knowledgePiece)).getName()));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger(LibNBT.knowledgePiece, 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote)
        	if(BBApi.getMaxBlood(playerIn) > 0) {
	            if(itemStackIn.hasTagCompound()) {
	                if(playerIn.isSneaking()) {
	                    int temp = itemStackIn.getTagCompound().getInteger(LibNBT.knowledgePiece);
	                    if(temp++ < BBKnowledge.getKnowledgeSize() - 1)
	                        itemStackIn.getTagCompound().setInteger(LibNBT.knowledgePiece, temp++);
	                    else if(temp++ >= BBKnowledge.getKnowledgeSize())
	                        itemStackIn.getTagCompound().setInteger(LibNBT.knowledgePiece, 0);
	                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	                } else if(itemStackIn.getTagCompound().getInteger(LibNBT.knowledgePiece) >= 0) {
	                    if(BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(LibNBT.knowledgePiece)) != null) {
	                    	ActionResult<ItemStack> temp = BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(LibNBT.knowledgePiece)).onKnowledgePieceRightClick(itemStackIn, worldIn, playerIn, hand);
	                    	if(temp != null && temp.getType() != null && temp.getType() == EnumActionResult.SUCCESS)
                    			BBApi.consumeBlood(playerIn, BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger(LibNBT.knowledgePiece)).getPrice());
	                    }
	                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	                }
	            } else {
	                itemStackIn.setTagCompound(new NBTTagCompound());
	                itemStackIn.getTagCompound().setInteger(LibNBT.knowledgePiece, 0);
	                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	            }
        	}
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
        	if(BBApi.getMaxBlood(playerIn) > 0)
	            if(stack.hasTagCompound() && stack.getTagCompound().getInteger(LibNBT.knowledgePiece) >= 0) {
	            	if(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(LibNBT.knowledgePiece)) != null) {
	            		EnumActionResult temp = BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(LibNBT.knowledgePiece)).onKnowledgePieceUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ); 
	            		if(temp != null && temp == EnumActionResult.SUCCESS)
            				BBApi.consumeBlood(playerIn, BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(LibNBT.knowledgePiece)).getPrice());
	            	}
	                return EnumActionResult.SUCCESS;
	            }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.worldObj.isRemote)
        	if(BBApi.getMaxBlood(playerIn) > 0)
	            if(stack.hasTagCompound() && stack.getTagCompound().getInteger(LibNBT.knowledgePiece) >= 0) {
	            	if(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(LibNBT.knowledgePiece)) != null)
	            		if(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(LibNBT.knowledgePiece)).knowledgePieceInteractionForEntity(stack, playerIn, target, hand))
	            			BBApi.consumeBlood(playerIn, BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger(LibNBT.knowledgePiece)).getPrice());
	                return true;
	            }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
