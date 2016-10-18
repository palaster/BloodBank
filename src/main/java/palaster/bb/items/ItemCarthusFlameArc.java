package palaster.bb.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.bb.api.capabilities.items.IFlameSpell;
import palaster.bb.entities.effects.BBPotions;
import palaster.libpal.items.ItemModSpecial;

public class ItemCarthusFlameArc extends ItemModSpecial implements IFlameSpell {

    public ItemCarthusFlameArc(ResourceLocation rl) {
        super(rl);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onPlayerAttackEntity(AttackEntityEvent e) {
    	if(!e.getEntityPlayer().worldObj.isRemote)
    		if(e.getTarget() instanceof EntityLivingBase)
    			if(e.getEntityPlayer().getHeldItemMainhand() != null && e.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemSword)
        			if(e.getEntityPlayer().getActivePotionEffect(BBPotions.carthusFlameArc) != null)
        				((EntityLivingBase) e.getTarget()).setFire(10);
    }

    @Override
    public int getSpellCost() { return 30; }

    @Override
    public boolean onSpellLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) { return false; }

    @Override
    public EnumActionResult onSpellUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) { return EnumActionResult.PASS; }

    @Override
    public ActionResult<ItemStack> onSpellRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        playerIn.addPotionEffect(new PotionEffect(BBPotions.carthusFlameArc, 3000, 0, false, true));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public boolean spellInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) { return false; }
}
