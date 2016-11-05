package palaster.bb.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import palaster.bb.BloodBank;
import palaster.bb.api.BBApi;
import palaster.bb.api.capabilities.entities.IRPG;
import palaster.bb.api.capabilities.entities.ITameableMonster;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityFactory;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityProvider;
import palaster.bb.api.capabilities.entities.RPGCapability.RPGCapabilityStorage;
import palaster.bb.api.capabilities.entities.TameableMonsterCapability.TameableMonsterCapabilityFactory;
import palaster.bb.api.capabilities.entities.TameableMonsterCapability.TameableMonsterCapabilityStorage;
import palaster.bb.api.capabilities.worlds.BBWorldCapability.BBWorldCapabilityFactory;
import palaster.bb.api.capabilities.worlds.BBWorldCapability.BBWorldCapabilityStorage;
import palaster.bb.api.capabilities.worlds.IBBWorld;
import palaster.bb.blocks.BBBlocks;
import palaster.bb.blocks.tile.TileEntityDesalinator;
import palaster.bb.blocks.tile.TileEntityVoidAnchor;
import palaster.bb.client.gui.GuiDesalinator;
import palaster.bb.client.gui.GuiRPGIntro;
import palaster.bb.client.gui.GuiVoidAnchor;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.core.handlers.BBEventHandler;
import palaster.bb.entities.BBEntities;
import palaster.bb.entities.effects.BBPotions;
import palaster.bb.inventories.ContainerDesalinator;
import palaster.bb.inventories.ContainerRPGIntro;
import palaster.bb.inventories.ContainerVoidAnchor;
import palaster.bb.items.BBItems;
import palaster.bb.items.ItemRPGIntro;
import palaster.bb.libs.LibMod;
import palaster.bb.network.client.UpdateRPGMessage;
import palaster.bb.network.server.GuiButtonMessage;
import palaster.bb.network.server.KeyClickMessage;
import palaster.bb.recipes.BBRecipes;
import palaster.libpal.core.helpers.LibPalHelper;
import palaster.libpal.network.PacketHandler;

public class CommonProxy implements IGuiHandler {
	
	public DamageSource bbBlood = (new DamageSource("bbBlood")).setDamageBypassesArmor().setMagicDamage();
	
	public void preInit() {
		CreativeTabBB.init();
		PacketHandler.registerMessage(GuiButtonMessage.class);
		PacketHandler.registerMessage(KeyClickMessage.class);
		PacketHandler.registerMessage(UpdateRPGMessage.class);
		BBBlocks.init();
		BBEntities.init();
		BBItems.init();
		BBPotions.init();
		LibPalHelper.setCreativeTab(LibMod.MODID, CreativeTabBB.tabBB);
		CapabilityManager.INSTANCE.register(IRPG.class, new RPGCapabilityStorage(), new RPGCapabilityFactory());
		CapabilityManager.INSTANCE.register(ITameableMonster.class, new TameableMonsterCapabilityStorage(), new TameableMonsterCapabilityFactory());
		CapabilityManager.INSTANCE.register(IBBWorld.class, new BBWorldCapabilityStorage(), new BBWorldCapabilityFactory());
		MinecraftForge.EVENT_BUS.register(new BBEventHandler());
	}
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(BloodBank.instance, this);
		BBApi.addItemStackToToken(new ItemStack(BBItems.leacher));
		BBApi.addItemStackToToken(new ItemStack(BBItems.horn));
	}
	
	public void postInit() { BBRecipes.init(); }
	
	public static void syncPlayerRPGCapabilitiesToClient(EntityPlayer player) {
		if(player != null && !player.worldObj.isRemote)
			if(RPGCapabilityProvider.get(player) != null)
				PacketHandler.sendTo(new UpdateRPGMessage(RPGCapabilityProvider.get(player).saveNBT()), (EntityPlayerMP) player);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 0: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemRPGIntro)
					return new ContainerRPGIntro();
				break;
			}
			case 1: {
				if(te != null && te instanceof TileEntityVoidAnchor)
					return new ContainerVoidAnchor(player.inventory, (TileEntityVoidAnchor) te);
				break;
			}
			case 2: {
				if(te != null && te instanceof TileEntityDesalinator)
					return new ContainerDesalinator(player.inventory, (TileEntityDesalinator) te);
				break;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
			case 0: {
				if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemRPGIntro)
					return new GuiRPGIntro(player);
				break;
			}
			case 1: {
				if(te != null && te instanceof TileEntityVoidAnchor)
					return new GuiVoidAnchor(player.inventory, (TileEntityVoidAnchor) te);
				break;
			}
			case 2: {
				if(te != null && te instanceof TileEntityDesalinator)
					return new GuiDesalinator(player.inventory, (TileEntityDesalinator) te);
				break;
			}
		}
		return null;
	}
}
