package palaster97.ss.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.libs.LibMod;

public class ItemMagicBow extends ItemModSpecial implements IDuctTapeable {
	
	public static final String[] bowPullIconNameArray = new String[] {"magicBow", "magicBow_pulling_0", "magicBow_pulling_1", "magicBow_pulling_2"};

	public ItemMagicBow() {
		super();
		setMaxDamage(1024);
		setUnlocalizedName("magicBow");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setItemRender(String name) {
		ModelBakery.addVariantName(this, new String[] { "ss:" + bowPullIconNameArray[0], "ss:" + bowPullIconNameArray[1], "ss:" + bowPullIconNameArray[2], "ss:" + bowPullIconNameArray[3]});
		setItemRender("magicBow", 0);
		setItemRender(bowPullIconNameArray[1], 1);
		setItemRender(bowPullIconNameArray[2], 2);
		setItemRender(bowPullIconNameArray[3], 3);
	}
	
	@SideOnly(Side.CLIENT)
	public void setItemRender(String name, int value) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, value, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
	
	@Override
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
		ModelResourceLocation modelresourcelocation = new ModelResourceLocation(LibMod.modid + ":magicBow", "inventory");
        if(stack.getItem() == this && player.getItemInUse() != null) {
            if(useRemaining >= 18)
                modelresourcelocation = new ModelResourceLocation(LibMod.modid + ":" + bowPullIconNameArray[3], "inventory");
            else if(useRemaining > 13)
                modelresourcelocation = new ModelResourceLocation(LibMod.modid + ":" + bowPullIconNameArray[2], "inventory");
            else if(useRemaining > 0)
                modelresourcelocation = new ModelResourceLocation(LibMod.modid + ":" + bowPullIconNameArray[1], "inventory");
        }
        return modelresourcelocation;
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
		int j = getMaxItemUseDuration(stack) - timeLeft;
        net.minecraftforge.event.entity.player.ArrowLooseEvent event = new net.minecraftforge.event.entity.player.ArrowLooseEvent(playerIn, stack, j);
        if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
        	return;
        j = event.charge;
        float f = (float)j / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if((double)f < 0.1D)
            return;
        if(f > 1.0F)
            f = 1.0F;
        EntityArrow entityarrow = new EntityArrow(worldIn, playerIn, f * 2.0F);
        if(f == 1.0F)
            entityarrow.setIsCritical(true);
        worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
        entityarrow.canBePickedUp = 2;
        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        if(!worldIn.isRemote) {
        	worldIn.spawnEntityInWorld(entityarrow);
        	stack.damageItem(1, playerIn);   
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) { return stack; }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 72000; }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) { return EnumAction.BOW; }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
    		net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(playerIn, itemStackIn);
            if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
            	return event.result;
            playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
        return itemStackIn;
    }
}
