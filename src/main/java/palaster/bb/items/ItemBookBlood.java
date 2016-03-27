package palaster.bb.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.entities.knowledge.BBKnowledge;

import java.util.List;

public class ItemBookBlood extends ItemModSpecial {

    public ItemBookBlood() {
        super();
        setUnlocalizedName("bookBlood");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        if(stack.hasTagCompound() && stack.getTagCompound().getInteger("Knowledge Piece") >= 0)
            tooltip.add(I18n.translateToLocal("bb.knowledgePiece") + ": " + I18n.translateToLocal(BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger("Knowledge Piece")).getName()));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("Knowledge Piece", 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(itemStackIn.getTagCompound() != null) {
            if(playerIn.isSneaking()) {
                int temp = itemStackIn.getTagCompound().getInteger("Knowledge Piece");
                if(temp++ < BBKnowledge.getKnowledgeSize() - 1)
                    itemStackIn.getTagCompound().setInteger("Knowledge Piece", temp++);
                else if(temp++ >= BBKnowledge.getKnowledgeSize())
                    itemStackIn.getTagCompound().setInteger("Knowledge Piece", 0);
            } else if(itemStackIn.getTagCompound().getInteger("Knowledge Piece") >= 0)
                BBKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger("Knowledge Piece")).onBookRightClick(itemStackIn, worldIn, playerIn);
        } else {
            itemStackIn.setTagCompound(new NBTTagCompound());
            itemStackIn.getTagCompound().setInteger("Knowledge Piece", 0);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(stack.getTagCompound() != null && stack.getTagCompound().getInteger("Knowledge Piece") >= 0)
                BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger("Knowledge Piece")).onBookUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.worldObj.isRemote)
            if(stack.getTagCompound() != null && stack.getTagCompound().getInteger("Knowledge Piece") >= 0)
                BBKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger("Knowledge Piece")).onBookInteract(stack, playerIn, target);
        return false;
    }
}
