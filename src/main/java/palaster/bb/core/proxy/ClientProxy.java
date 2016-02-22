package palaster.bb.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.BlockMod;
import palaster.bb.entities.BBEntities;
import palaster.bb.items.*;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		BBEntities.registerEntityRenderers();
	}

	@Override
	public void init() {
		super.init();
		// Blocks
		((BlockMod) BBBlocks.soulCompressor).setBlockRender(BBBlocks.soulCompressor.getUnlocalizedName().substring(5));
		((BlockMod) BBBlocks.playerManipulator).setBlockRender(BBBlocks.playerManipulator.getUnlocalizedName().substring(5));
		((BlockMod) BBBlocks.worldManipulator).setBlockRender(BBBlocks.worldManipulator.getUnlocalizedName().substring(5));
		((BlockMod) BBBlocks.voidAnchor).setBlockRender(BBBlocks.voidAnchor.getUnlocalizedName().substring(5));
		((BlockMod) BBBlocks.touchVoid).setBlockRender(BBBlocks.touchVoid.getUnlocalizedName().substring(5));

		// Items
		((ItemMod) BBItems.playerBinder).setItemRender(BBItems.playerBinder.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.worldBinder).setItemRender(BBItems.worldBinder.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.staffSkeleton).setItemRender(BBItems.staffSkeleton.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.staffEfreet).setItemRender(BBItems.staffEfreet.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.staffTime).setItemRender(BBItems.staffTime.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.staffVoidWalker).setItemRender(BBItems.staffVoidWalker.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.staffHungryShadows).setItemRender(BBItems.staffHungryShadows.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.animalHerder).setItemRender(BBItems.animalHerder.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.magicDuctTape).setItemRender(BBItems.magicDuctTape.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.tapeHeart).setItemRender(BBItems.tapeHeart.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.hephaestusHammer).setItemRender(BBItems.hephaestusHammer.getUnlocalizedName().substring(5));
		((ItemTrident) BBItems.trident).setItemRender(BBItems.trident.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.athame).setItemRender(BBItems.athame.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.bloodBook).setItemRender(BBItems.bloodBook.getUnlocalizedName().substring(5));

		((ItemMod) BBItems.debug).setItemRender(BBItems.debug.getUnlocalizedName().substring(5));
		((ItemMod) BBItems.yinYang).setItemRender(BBItems.yinYang.getUnlocalizedName().substring(5));

		// Armor
		((BBArmor) BBItems.boundHelmet).setItemRender(BBItems.boundHelmet.getUnlocalizedName().substring(5));
		((BBArmor) BBItems.boundChestplate).setItemRender(BBItems.boundChestplate.getUnlocalizedName().substring(5));
		((BBArmor) BBItems.boundLeggings).setItemRender(BBItems.boundLeggings.getUnlocalizedName().substring(5));
		((BBArmor) BBItems.boundBoots).setItemRender(BBItems.boundBoots.getUnlocalizedName().substring(5));

		for(int i = 0; i < ItemBBResources.names.length; i++)
 			((ItemBBResources) BBItems.bbResources).setItemRender(ItemBBResources.names[i], i);
	}

	@Override
	public void postInit() { super.postInit(); }

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx)); }
	
	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx)); }
}
