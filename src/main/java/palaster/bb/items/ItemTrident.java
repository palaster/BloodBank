package palaster.bb.items;

import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTrident extends ItemSword implements IDuctTapeable {

	public ItemTrident(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
		setCreativeTab(CreativeTabBB.tabSS);
		setUnlocalizedName("trident");
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		GameRegistry.registerItem(this, unlocalizedName);
		return super.setUnlocalizedName(unlocalizedName);
	}
	
	@SideOnly(Side.CLIENT)
	public void setItemRender(String name) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMod.modid + ":" + name, "inventory")); }
}