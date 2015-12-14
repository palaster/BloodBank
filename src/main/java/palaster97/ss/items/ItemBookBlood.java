package palaster97.ss.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.entities.knowledge.SSKnowledge;
import palaster97.ss.entities.knowledge.SSKnowledgePiece;

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
            tooltip.add(StatCollector.translateToLocal("ss.knowledgePiece") + ": " + StatCollector.translateToLocal(SSKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger("Knowledge Piece")).getName()));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("Knowledge Piece", -1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!worldIn.isRemote)
            if(itemStackIn.getTagCompound() != null) {
                if(playerIn.isSneaking()) {
                    int temp = itemStackIn.getTagCompound().getInteger("Knowledge Piece");
                    if(temp++ == SSKnowledge.getKnowledgeSize() - 1 || temp == -1)
                        itemStackIn.getTagCompound().setInteger("Knowledge Piece", 0);
                    else
                        itemStackIn.getTagCompound().setInteger("Knowledge Piece", temp++);
                }
            } else if(itemStackIn.getTagCompound().getInteger("Knowledge Piece") >= 0)
                SSKnowledge.getKnowledgePiece(itemStackIn.getTagCompound().getInteger("Knowledge Piece")).onBookRightClick(itemStackIn, worldIn, playerIn);
        return itemStackIn;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            if(stack.getTagCompound() != null && stack.getTagCompound().getInteger("Knowledge Piece") >= 0)
                SSKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger("Knowledge Piece")).onBookUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
        return false;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        if(!playerIn.worldObj.isRemote)
            if(stack.getTagCompound() != null && stack.getTagCompound().getInteger("Knowledge Piece") >= 0)
                SSKnowledge.getKnowledgePiece(stack.getTagCompound().getInteger("Knowledge Piece")).onBookInteract(stack, playerIn, target);
        return true;
    }
}
