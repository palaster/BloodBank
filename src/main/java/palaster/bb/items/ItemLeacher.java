package palaster.bb.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.helpers.BBItemStackHelper;
import palaster.bb.libs.LibMod;
import palaster.bb.libs.LibNBT;

public class ItemLeacher extends ItemSword {

	public ItemLeacher(ToolMaterial material) {
		super(material);
		setUnlocalizedName("leacher");
		setCreativeTab(CreativeTabBB.tabBB);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			stack = BBItemStackHelper.addNumberTagToItemStack(stack, LibNBT.amount, 0);
		}
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		setRegistryName(new ResourceLocation(LibMod.modid, unlocalizedName));
		GameRegistry.register(this);
		return super.setUnlocalizedName(LibMod.modid + ":" + unlocalizedName);
	}
}
