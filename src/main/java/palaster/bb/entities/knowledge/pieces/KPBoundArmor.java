package palaster.bb.entities.knowledge.pieces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemBoundArmor;

public class KPBoundArmor implements IKnowledgePiece {

    @Override
    public String getName() { return "bb.kp.boundArmor"; }

    @Override
    public int getPrice() { return 3000; }

    @Override
    public ActionResult<ItemStack> onKnowledgePieceRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        for(int i = 0; i < 4; i++) {
            if(playerIn.inventory.armorInventory[i] != null) {
            	ItemStack boundArmor = new ItemStack(BBItems.boundHelmet);
            	switch(i) {
            		case 3: boundArmor = new ItemStack(BBItems.boundHelmet);
            		break;
            		case 2: boundArmor = new ItemStack(BBItems.boundChestplate);
            		break;
            		case 1: boundArmor = new ItemStack(BBItems.boundLeggings);
            		break;
            		case 0: boundArmor = new ItemStack(BBItems.boundBoots);
            		break;
            	}
            	playerIn.inventory.armorInventory[i] = BBItemStackHelper.setItemStackInsideItemStack(boundArmor, playerIn.inventory.armorInventory[i], ItemBoundArmor.TAG_TAG_INSIDE_BOUND);
            } else {
                ItemStack boundArmor = new ItemStack(BBItems.boundHelmet);
                switch(i) {
                    case 3:
                        boundArmor = new ItemStack(BBItems.boundHelmet);
                        break;
                    case 2:
                        boundArmor = new ItemStack(BBItems.boundChestplate);
                        break;
                    case 1:
                        boundArmor = new ItemStack(BBItems.boundLeggings);
                        break;
                    case 0:
                        boundArmor = new ItemStack(BBItems.boundBoots);
                        break;
                }
                playerIn.inventory.armorInventory[i] = boundArmor;
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public EnumActionResult onKnowledgePieceUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return EnumActionResult.PASS; }

    @Override
    public boolean knowledgePieceInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) { return false; }
}
