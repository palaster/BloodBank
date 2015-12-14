package palaster97.ss.entities.knowledge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class SSKnowledgePiece {

    private static String name;
    private static int price;

    public SSKnowledgePiece(String name, int price) {
        this.name = name;
        this.price = price;
        SSKnowledge.addKnowledgePiece(this);
    }

    public String getName() { return name; }

    public int getPrice() { return price; }

    public abstract void onBookInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target);

    public abstract void onBookRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn);

    public abstract void onBookUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ);
}
