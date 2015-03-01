package palaster97.ss.items;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.core.helpers.SSPlayerHelper;
import palaster97.ss.libs.LibMod;

public class ItemModRecord extends ItemRecord {

	public ItemModRecord(String name) {
		super(name);
		setCreativeTab(CreativeTabSS.tabSS);
		setMaxDamage(0);
		setUnlocalizedName("ssRecord." + name);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("PlayerUUID", playerIn.getUniqueID().toString());
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		GameRegistry.registerItem(this, unlocalizedName, LibMod.modid);
		setItemRender(unlocalizedName);
		return super.setUnlocalizedName(unlocalizedName);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		if(stack.hasTagCompound()) {
			EntityPlayer p = SSPlayerHelper.getPlayerFromDimensions(stack.getTagCompound().getString("PlayerUUID"));
			if(p != null)
				tooltip.add(p.getDisplayNameString());
			else
				tooltip.add("Player is either offline or broken UUID");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setItemRender(String name) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }

	@Override
    @SideOnly(Side.CLIENT)
    public String getRecordNameLocal() { return StatCollector.translateToLocal("item.record." + recordName + ".desc"); }

    @Override
    public ResourceLocation getRecordResource(String name) { return new ResourceLocation(LibMod.modid + ":" + name); }
}