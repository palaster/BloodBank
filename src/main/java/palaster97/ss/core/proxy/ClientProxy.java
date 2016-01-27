package palaster97.ss.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster97.ss.blocks.BlockMod;
import palaster97.ss.blocks.SSBlocks;
import palaster97.ss.entities.SSEntities;
import palaster97.ss.items.ItemMod;
import palaster97.ss.items.ItemSSResources;
import palaster97.ss.items.ItemTrident;
import palaster97.ss.items.SSItems;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		SSEntities.registerEntityRenderers();
	}

	@Override
	public void init() {
		super.init();
		// Blocks
		((BlockMod) SSBlocks.soulCompressor).setBlockRender(SSBlocks.soulCompressor.getUnlocalizedName().substring(5));
		((BlockMod) SSBlocks.playerManipulator).setBlockRender(SSBlocks.playerManipulator.getUnlocalizedName().substring(5));
		((BlockMod) SSBlocks.worldManipulator).setBlockRender(SSBlocks.worldManipulator.getUnlocalizedName().substring(5));
		((BlockMod) SSBlocks.voidAnchor).setBlockRender(SSBlocks.voidAnchor.getUnlocalizedName().substring(5));
		((BlockMod) SSBlocks.touchVoid).setBlockRender(SSBlocks.touchVoid.getUnlocalizedName().substring(5));

		// Items
		((ItemMod) SSItems.playerBinder).setItemRender(SSItems.playerBinder.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.worldBinder).setItemRender(SSItems.worldBinder.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.staffSkeleton).setItemRender(SSItems.staffSkeleton.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.staffEfreet).setItemRender(SSItems.staffEfreet.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.staffTime).setItemRender(SSItems.staffTime.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.staffVoidWalker).setItemRender(SSItems.staffVoidWalker.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.staffHungryShadows).setItemRender(SSItems.staffHungryShadows.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.animalHerder).setItemRender(SSItems.animalHerder.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.magicDuctTape).setItemRender(SSItems.magicDuctTape.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.tapeHeart).setItemRender(SSItems.tapeHeart.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.hephaestusHammer).setItemRender(SSItems.hephaestusHammer.getUnlocalizedName().substring(5));
		((ItemTrident) SSItems.trident).setItemRender(SSItems.trident.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.athame).setItemRender(SSItems.athame.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.bloodBook).setItemRender(SSItems.bloodBook.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.bloodPact).setItemRender(SSItems.bloodPact.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.debug).setItemRender(SSItems.debug.getUnlocalizedName().substring(5));
		((ItemMod) SSItems.yinYang).setItemRender(SSItems.yinYang.getUnlocalizedName().substring(5));
		for(int i = 0; i < ItemSSResources.names.length; i++)
 			((ItemSSResources) SSItems.ssResources).setItemRender(ItemSSResources.names[i], i);
	}

	@Override
	public void postInit() { super.postInit(); }

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx)); }
	
	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) { return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx)); }
}
