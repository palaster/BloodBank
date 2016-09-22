package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.bb.api.capabilities.items.IFlameSpell;
import palaster.bb.entities.effects.BBPotions;

public class ItemSacredFlame extends ItemModSpecial implements IFlameSpell {

    public ItemSacredFlame() {
        super();
        setUnlocalizedName("sacredFlame");
    }

    @Override
    public int getSpellCost() { return 25; }

    @Override
    public boolean onSpellLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) { return false; }

    @Override
    public EnumActionResult onSpellUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return EnumActionResult.PASS; }

    @Override
    public ActionResult<ItemStack> onSpellRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) { return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn); }

    @Override
    public boolean spellInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(target instanceof EntityLiving || target instanceof EntityPlayer)
        	if(target.getActivePotionEffect(BBPotions.timedFlame) == null) {
        		target.addPotionEffect(new PotionEffect(BBPotions.timedFlame, 600, 0, false, true));
            	return true;
        	}
        return false;
    }
}
