package palaster97.ss.inventories;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;
import palaster97.ss.blocks.tile.TileEntityPlayerStatue;

public class ContainerPlayerStatue extends Container {
	
	private int value;
	private SaveHandler saveHandler;
	private NBTTagCompound playerNBT;
	private InventoryPlayer victim;
	
	public ContainerPlayerStatue(InventoryPlayer invPlayer, TileEntityPlayerStatue ps, int value) {
		this.value = value;
		if(value == 1) {
			if(invPlayer.player.capabilities.isCreativeMode) {
				if(MinecraftServer.getServer().getConfigurationManager().canSendCommands(invPlayer.player.getGameProfile())) {
					String name = ps.getUUIDName();
					if(name != null) {
						saveHandler = (SaveHandler) DimensionManager.getWorld(0).getSaveHandler();
						if(name.matches("[0-9a-f]{8}-[0-9a-f]{4}-[34][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
							EntityPlayer p = DimensionManager.getWorld(0).getPlayerEntityByUUID(UUID.fromString(name));
							if(p != null) {
								playerNBT = saveHandler.getPlayerNBTManager().readPlayerData(p);
								victim = new InventoryPlayer(null);
								victim.readFromNBT((NBTTagList) playerNBT.getTag("Inventory"));

								for(int x = 0; x < 9; x++)
									addSlotToContainer(new Slot(victim, x, 8 + 18 * x, 76));
								for(int y = 0; y < 3; y++)
									for(int x = 0; x < 9; x++)
										addSlotToContainer(new Slot(victim, x + y * 9 + 9, 8 + 18 * x, 8 + y * 18));
							}
						}
					}
				}				
			}
			for(int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 160));
			for(int y = 0; y < 3; y++)
				for(int x = 0; x < 9; x++)
					addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 102 + y * 18));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) { return true; }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) { return null; }
	
	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		if(value == 1) {
			try {
			    File playersDirectory = new File(saveHandler.getWorldDirectory(), "playerdata");
			    File temp = new File(playersDirectory, victim.player.getUniqueID() + ".dat.tmp");
			    File playerFile = new File(playersDirectory, victim.player.getUniqueID() + ".dat");
			    CompressedStreamTools.writeCompressed(playerNBT, new FileOutputStream(temp));
			    if(playerFile.exists())
			        playerFile.delete();
			    temp.renameTo(playerFile);
			} catch(Exception e) {}
		}
		super.onContainerClosed(p_75134_1_);
	}
}